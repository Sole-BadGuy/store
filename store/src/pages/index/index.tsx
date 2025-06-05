import { useState, useEffect } from 'react'
import { View, Text, Input, Button, ScrollView } from '@tarojs/components'
import Taro from '@tarojs/taro'
import './index.less'

export default function OrderPage() {
  const [products, setProducts] = useState<INDEX.Products[]>([])
  // 存储选中的商品及其数量 { productId: quantity }
  const [selectedProducts, setSelectedProducts] = useState<Record<number, number>>({})
  const [orderView, setOrderView] = useState<INDEX.Order[]>([])
  const [loading, setLoading] = useState(false)

  // 获取商品列表
  useEffect(() => {
    fetchProducts()
  }, [])

  const fetchProducts = async () => {
    try {
      setLoading(true)
      const res = await Taro.request({
        url: 'http://127.0.0.1:8080/products',
        method: 'GET'
      })
      setProducts(res.data.data)
    } catch (error) {
      Taro.showToast({
        title: '获取商品列表失败',
        icon: 'none'
      })
    } finally {
      setLoading(false)
    }
  }

  // 切换商品选择状态
  const toggleProductSelection = (productId: number) => {
    setSelectedProducts(prev => {
      const newSelection = { ...prev }
      if (newSelection[productId]) {
        delete newSelection[productId] // 取消选择
      } else {
        newSelection[productId] = 1 // 默认数量为1
      }
      return newSelection
    })
  }

  // 更新商品数量
  const updateProductQuantity = (productId: number, value: string) => {
    const quantity = parseInt(value) || 0
    if (quantity < 0) return

    setSelectedProducts(prev => {
      return { ...prev, [productId]: quantity }
    })
  }

  // 计算总价
  const calculateTotalPrice = () => {
    return Object.entries(selectedProducts).reduce((total, [productId, quantity]) => {
      const product = products.find(p => p.id === Number(productId))
      return total + (product?.price || 0) * quantity
    }, 0)
  }

  // 提交订单
  const placeOrder = async () => {
    const selectedCount = Object.keys(selectedProducts).length
    if (selectedCount === 0) {
      Taro.showToast({
        title: '请至少选择一件商品',
        icon: 'none'
      })
      return
    }
    try {
      setLoading(true)
      const orderItems: INDEX.OrderItem[] = Object.entries(selectedProducts).map(
        ([productId, quantity]) => ({
          productId: Number(productId),
          quantity
        })
      )
      const res = await Taro.request({
        url: 'http://127.0.0.1:8080/orders',
        method: 'POST',
        data: {
          orders: orderItems.filter(item=>item.quantity>0)
        }
      })
      if(res.data.code===200){
        setOrderView([...orderView,...res.data.data])
        fetchProducts()
        // 清空选择
        setSelectedProducts({})

        Taro.showToast({
          title: '下单成功',
          icon: 'success'
        })
      }else{
        Taro.showToast({
          title: res.data.message,
          icon: 'none'
        })
      }
    } catch (error) {
      Taro.showToast({
        title: '下单失败',
        icon: 'none'
      })
    } finally {
      setLoading(false)
    }
  }

  return (
    <View className='order-page'>
      <ScrollView scrollY className='product-list'>
        <Text className='title'>商品列表 (点击选择/取消)</Text>
        {loading && !products.length ? (
          <Text>加载中...</Text>
        ) : (
          products.map(product => {
            const isSelected = selectedProducts.hasOwnProperty(product.id)
            const quantity = isSelected ? selectedProducts[product.id] : 0

            return (
              <View
                key={product.id}
                className={`product-item ${isSelected ? 'selected' : ''}`}
                onClick={() => toggleProductSelection(product.id)}
              >
                <View className="product-info">
                  <Text className='product-name'>{product.name}</Text>
                  <Text className='product-price'>¥{product.price.toFixed(2)}</Text>
                  <Text className='product-stock'>剩余库存:{product.stock}</Text>
                </View>

                {isSelected && (
                  <View className="quantity-control" onClick={e => e.stopPropagation()}>
                    <Text>数量:</Text>
                    <Input
                      type='number'
                      value={quantity.toString()}
                      onInput={(e) => updateProductQuantity(product.id, e.detail.value)}
                      className='quantity-input'
                    />
                  </View>
                )}
              </View>
            )
          })
        )}
      </ScrollView>
      <ScrollView scrollY className='product-list'>
        <Text className='title'>订单详情</Text>
      {orderView && (
        <View className='order-confirmation'>
          <Text className='section-title'>订单确认</Text>
          {orderView.map((item, index) => (
            <View key={index} className="order-item">
              <Text>订单号: {item.id}</Text>
              <Text>{item.product.name} × {item.quantity}</Text>
          <View className="order-total">
            <Text>总金额: ¥{item.totalPrice.toFixed(2)}</Text>
          </View>
          <Text>下单时间: {new Date().toLocaleString()}</Text>
            </View>
          ))}
        </View>
      )}
      </ScrollView>

      {Object.keys(selectedProducts).length > 0 && (
        <View className='order-form'>
          <Text className='section-title'>订单信息</Text>

          <View className="selected-products">
            {Object.entries(selectedProducts).map(([productId, quantity]) => {
              const product = products.find(p => p.id === Number(productId))
              if (!product||quantity<=0) return null

              return (
                <View key={productId} className="selected-item">
                  <Text>{product.name} × {quantity}</Text>
                  <Text>¥{(product.price * quantity).toFixed(2)}</Text>
                </View>
              )
            })}
          </View>

          <View className='total-price'>
            <Text>总价: ¥{calculateTotalPrice().toFixed(2)}</Text>
          </View>

          <Button
            className='submit-btn'
            onClick={placeOrder}
            loading={loading}
            disabled={loading}
          >
            提交订单
          </Button>
        </View>
      )}
    </View>
  )
}
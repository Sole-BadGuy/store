# node版本 18.18.0
# 使用 npm 安装依赖
  npm install
# 执行编译命令
  npm run dev:weapp
# 使用微信开发者工具打开当前项目(因为是本地ip记得打开不校验合法域名以及证书)
微信开发者工具右上角->详情->本地设置->不校验合法域名......

 # 后端idea打开后直接moven下载需要的包启动就行了

 # 接口1 获取商品列表-无参
   127.0.0.1:8080/products
   curl 'http://127.0.0.1:8080/products' \
  -H 'Connection: keep-alive' \
  -H 'User-Agent: Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1 wechatdevtools/1.06.2210141 MicroMessenger/8.0.5 Language/zh_CN webview/' \
  -H 'content-type: application/json' \
  -H 'Accept: */*' \
  -H 'Sec-Fetch-Site: same-site' \
  -H 'Sec-Fetch-Mode: cors' \
  -H 'Sec-Fetch-Dest: empty' \
  -H 'Referer: https://servicewechat.com/wxa45c0ecd1a0c2a6e/devtools/page-frame.html' \
  --compressed
 # 接口2 下单 将商品id和数量传入即可
    127.0.0.1:8080/orders
    curl 'http://127.0.0.1:8080/products' \
  -H 'Connection: keep-alive' \
  -H 'User-Agent: Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A372 Safari/604.1 wechatdevtools/1.06.2210141 MicroMessenger/8.0.5 Language/zh_CN webview/' \
  -H 'content-type: application/json' \
  -H 'Accept: */*' \
  -H 'Sec-Fetch-Site: same-site' \
  -H 'Sec-Fetch-Mode: cors' \
  -H 'Sec-Fetch-Dest: empty' \
  -H 'Referer: https://servicewechat.com/wxa45c0ecd1a0c2a6e/devtools/page-frame.html' \
  --compressed

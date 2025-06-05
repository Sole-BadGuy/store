declare namespace INDEX {
    interface Products {
      id:number;
      name: string;
      price: number;
      stock:number;
    }
    interface Order {
      id:number;
      productId:number;
      quantity:number;
      totalPrice:number;
      orderDate:string;
      product:Products;
    }

interface OrderItem  {
  productId: number;
  quantity: number;
}
}

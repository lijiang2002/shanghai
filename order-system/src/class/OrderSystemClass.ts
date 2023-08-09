// 订单类
export class Order {
    date: number | null = null;
    description: string = '';
    express: string = '';
    id: string = '';
    price: number = 0;
    product: string | null = null;
    qUser: User = new User();
    user: User = new User();
    orde: string = ''
}

// 用户类
export class User {
    account: string = '';
    password: string = '';
    status: number = 0;
    username: string = '';
    id: string = '';
}

// 城市类
export class City {
    city: string = '';
    description: string = '';
    id: string = '';

}


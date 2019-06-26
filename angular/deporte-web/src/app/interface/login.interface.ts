export interface LoginResponse {
    token: string;
    user: {
        id: string,
        name: string,
        picture: string,
        email: string,
        type: string,
        role:string,
        friend: string[],
        locked: boolean,
        createdAt: string
    }
}

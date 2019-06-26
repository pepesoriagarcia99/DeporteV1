export interface UserList {
    id: string;
    name: string;
    picture:string;
    email:string;
    type:string;
    friend: string[];
    locked: boolean;
    role:string;
}
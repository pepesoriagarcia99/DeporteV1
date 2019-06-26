import { Injectable } from '@angular/core';

export interface BadgeItem {
  type: string;
  value: string;
}

export interface ChildrenItems {
  state: string;
  name: string;
  type?: string;
}

export interface Menu {
  state: string;
  name: string;
  type: string;
  icon: string;
  badge?: BadgeItem[];
  children?: ChildrenItems[];
}

const MENUITEMS = [
  {
    state: '/inicio',
    name: 'INICIO',
    type: 'link',
    icon: 'home'
  },
  {
    state: '/inicio/user',
    name: 'USUARIOS',
    type: 'link',
    icon: 'person'
  },
  {
    state: '/inicio/groups',
    name: 'GRUPOS',
    type: 'link',
    icon: 'group'
  },
  {
    state: '/inicio/sport',
    name: 'DEPORTES',
    type: 'link',
    icon: 'directions_bike'
  }
];

@Injectable()
export class MenuService {
  getAll(): Menu[] {
    return MENUITEMS;
  }

  add(menu) {
    MENUITEMS.push(menu);
  }
}

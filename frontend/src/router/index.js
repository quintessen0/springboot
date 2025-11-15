import { createRouter, createWebHistory } from 'vue-router'
import Login from '../views/Login.vue'
import Dashboard from '../views/Dashboard.vue'
import Department from '../views/Department.vue'
import Position from '../views/Position.vue'
import Employee from '../views/Employee.vue'
import Attendance from '../views/Attendance.vue'
import.meta.env.BASE_URL

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard,
    redirect: '/dashboard/department',
    children: [
      {
        path: 'department',
        name: 'Department',
        component: Department
      },
      {
        path: 'position',
        name: 'Position',
        component: Position
      },
      {
        path: 'employee',
        name: 'Employee',
        component: Employee
      },
      {
        path: 'attendance',
        name: 'Attendance',
        component: Attendance
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router

import request from './request'

// 获取所有员工
export const getEmployees = () => {
  return request({
    url: '/employees',
    method: 'get'
  })
}

// 条件查询员工
export const queryEmployees = (params) => {
  return request({
    url: '/employees/query',
    method: 'get',
    params
  })
}

// 新增员工
export const addEmployee = (data) => {
  return request({
    url: '/employees',
    method: 'post',
    data
  })
}

// 更新员工
export const updateEmployee = (data) => {
  return request({
    url: '/employees',
    method: 'put',
    data
  })
}

// 删除员工
export const deleteEmployee = (empId) => {
  return request({
    url: `/employees/${empId}`,
    method: 'delete'
  })
}

// 根据ID获取员工
export const getEmployeeById = (empId) => {
  return request({
    url: `/employees/${empId}`,
    method: 'get'
  })
}
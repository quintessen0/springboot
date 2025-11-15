import request from './request'

// 部门相关API
export const getDepartmentList = () => {
  return request({
    url: '/department/list',
    method: 'get'
  })
}

export const addDepartment = (data) => {
  return request({
    url: '/department/add',
    method: 'post',
    data
  })
}

export const updateDepartment = (data) => {
  return request({
    url: '/department/update',
    method: 'put',
    data
  })
}

export const deleteDepartment = (deptId) => {
  return request({
    url: `/department/delete/${deptId}`,
    method: 'delete'
  })
}

export const getDepartmentById = (deptId) => {
  return request({
    url: `/department/${deptId}`,
    method: 'get'
  })
}

// 为了保持兼容性，添加getDepartments函数
export const getDepartments = getDepartmentList

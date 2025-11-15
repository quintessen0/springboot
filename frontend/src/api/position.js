import request from './request'

// 获取所有职位
export const getPositions = () => {
  return request({
    url: '/positions',
    method: 'get'
  })
}

// 根据部门ID获取职位
export const getPositionsByDept = (deptId) => {
  return request({
    url: `/positions/dept/${deptId}`,
    method: 'get'
  })
}

// 新增职位
export const addPosition = (data) => {
  return request({
    url: '/positions',
    method: 'post',
    data
  })
}

// 更新职位
export const updatePosition = (data) => {
  return request({
    url: '/positions',
    method: 'put',
    data
  })
}

// 删除职位
export const deletePosition = (posId) => {
  return request({
    url: `/positions/${posId}`,
    method: 'delete'
  })
}

// 根据ID获取职位
export const getPositionById = (posId) => {
  return request({
    url: `/positions/${posId}`,
    method: 'get'
  })
}
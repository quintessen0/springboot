import request from './request'

// 获取考勤记录列表
export const getAttendances = (params) => {
  return request({
    url: '/attendances/page',
    method: 'get',
    params
  })
}

// 根据员工ID获取考勤记录
export const getAttendancesByEmp = (empId) => {
  return request({
    url: `/attendances/employee/${empId}`,
    method: 'get'
  })
}

// 新增考勤记录
export const addAttendance = (data) => {
  return request({
    url: '/attendances',
    method: 'post',
    data
  })
}

// 更新考勤记录
export const updateAttendance = (data) => {
  return request({
    url: `/attendances/${data.attId}`,
    method: 'put',
    data
  })
}

// 删除考勤记录
export const deleteAttendance = (attId) => {
  return request({
    url: `/attendances/${attId}`,
    method: 'delete'
  })
}

// 根据ID获取考勤记录
export const getAttendanceById = (attId) => {
  return request({
    url: `/attendances/${attId}`,
    method: 'get'
  })
}
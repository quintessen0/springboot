import request from './request'

// 登录API
export const login = (data) => {
  return request({
    url: '/login/doLogin',
    method: 'post',
    data
  })
}

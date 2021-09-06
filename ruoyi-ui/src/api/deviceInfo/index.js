import request from '@/utils/request'

// 查询设备详情
export function getInfo() {
  return request({
    url: '/deviceInfo',
    method: 'get',
  })
}

// 查询设备传感器
export function getProperties() {
  return request({
    url: '/deviceProperties',
    method: 'get',
  })
}
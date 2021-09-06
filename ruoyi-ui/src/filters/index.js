// 日期格式化
export function formatDates (time, fmt) {
  if (time == null) {
    return '-'
  }
  const date = new Date(time)
  fmt = fmt || 'yyyy-MM-dd hh:mm:ss'
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
  }
  const o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds()
  }
  for (var k in o) {
    if (new RegExp(`(${k})`).test(fmt)) {
      const str = o[k] + ''
      fmt = fmt.replace(RegExp.$1, RegExp.$1.length === 1 ? str : ('00' + str).substr(str.length))
    }
  }
  return fmt
}

// 设备列表-参数设置-定位方式
export function filterLocationType (status) {
  const mapStatus = {
    0: 'GNNS',
    1: 'LBS'
  }
  return mapStatus[status] ? mapStatus[status] : ''
}
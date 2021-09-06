import Vue from 'vue/dist/vue.esm.js'
// _this:页面vue的this
// vue组件
export function getVue (_this, position) {
  // Infowindow中添加vue组件
  const MyComponent = Vue.extend({
    data () {
      return {
        resource: _this.resourceTxt,
        commonResource: _this.commonResourceTxt,
        permission: _this.permission,
        positionInfo: _this.positionInfo,
        locationName: _this.location_name,
        locationNameShow: false
      }
    },
    mounted () {
      if (position) {
        this.positionInfo.lng = position.lng.toFixed(6)
        this.positionInfo.lat = position.lat.toFixed(6)
      }
    },
    methods: {
      defaultContentBtnFn (val) {
        _this.$emit('defaultContentBtnFn', val)
      },
      getAddressByGeocoder (locationNameShow) {
        _this.getAddressByGeocoder((location) => {
          this.locationNameShow = locationNameShow
          this.locationName = location
        })
      }
    },
    template: _this.infowindowOptions.content

  })
  const component = new MyComponent().$mount()
  const parent = document.getElementById('parent')
  const childs = parent.getElementsByTagName('div')
  if (childs.length > 0) {
    const child = parent.children[0]
    parent.removeChild(child)
    parent.appendChild(component.$el)
  }
}

// 实时跟踪
export function timeTrack (_this, options, callBack) {
  _this.timeTrackPoints = []
  // 点聚合（点击marker获取对应this.deviceId）
  _this.getPositionInfo(_this.deviceId || _this.paramsMarkerInfo.deviceId, () => {
    if (_this.type === 'bmap') {
      bmapTimeTrack(_this, options, callBack)
    } else if (_this.type === 'amap') {
      amapTimeTrack(_this, options, callBack)
    } else if (_this.type === 'google') {
      gmapTimeTrack(_this, options, callBack)
    }
  })
  _this.timer = setInterval(() => {
    _this.getPositionInfo(_this.deviceId || _this.paramsMarkerInfo.deviceId, () => {
      if (_this.type === 'bmap') {
        bmapTimeTrack(_this, options, callBack)
      } else if (_this.type === 'amap') {
        amapTimeTrack(_this, options, callBack)
      } else if (_this.type === 'google') {
        gmapTimeTrack(_this, options, callBack)
      }
    })
  }, 15000)
}
// 实时跟踪-百度
function bmapTimeTrack (_this) {
  const pointNew = { lng: _this.positionInfo.lng, lat: _this.positionInfo.lat }
  // const pointNew = { lng: Math.random() * 80 + 80, lat: Math.random() * 30 + 30 }
  if (_this.timeTrackPoints.length > 1) {
    const last = _this.timeTrackPoints[_this.timeTrackPoints.length - 1]
    // 判断是否重复点
    if (last.lng === pointNew.lng && last.lat === pointNew.lat) {
      return
    }
  }
  _this.timeTrackPoints = [..._this.timeTrackPoints, ...[pointNew]] // 所有点

  const point = _this.bmapUtils.createPoint(pointNew)
  const icon = _this.bmapUtils.createIcon({
    url: _this.positionInfo.productFileUrl || _this.markerIconOptions.url,
    size: _this.markerIconOptions.size
  })
  const marker = _this.bmapUtils.createMarker({
    icon,
    point,
    offset: { x: 0, y: 0 }
  })
  // 设置居中
  const options = {
    path: _this.timeTrackPoints
  }
  _this.bmapUtils.setFitView(options)
  // 先清-再打点，划线
  _this.markerClusterer && _this.markerClusterer.clearMarkers()
  _this.bmapUtils.clearOverlays()
  _this.bmapUtils.addOverlay({ overlay: marker })
  bmapDrawLine(_this)

  const infowindowOptions = {
    content: '<div id="parent"><div></div></div>',
    width: _this.infowindowOptions.width, // 信息窗口宽度
    height: _this.infowindowOptions.height, // 信息窗口高度
    offset: {
      width: -32, height: -5
    },
    title: '', // 信息窗口标题
    enableMessage: true // 设置允许信息窗发送短息
  }
  const infowindow = _this.bmapUtils.createInfoWindow(infowindowOptions)
  const openInfoWindowOptions = {
    infowindow,
    point
  }
  _this.bmapUtils.openInfoWindow(openInfoWindowOptions)
  // 处理infowindow数据加载
  setTimeout(() => {
    _this.getVue(false, pointNew)
  }, 20)
  marker.addEventListener('click', () => {
    _this.bmapUtils.openInfoWindow(openInfoWindowOptions)
    _this.getVue(false, pointNew)
  })
}
// 实时跟踪-高德
function amapTimeTrack (_this) {

}
// 实时跟踪-谷歌
function gmapTimeTrack (_this) {
  const pointNew = { lng: _this.positionInfo.lng, lat: _this.positionInfo.lat }
  // const pointNew = { lng: Math.random() * 80 + 80, lat: Math.random() * 30 + 30 }
  if (_this.timeTrackPoints.length > 1) {
    const last = _this.timeTrackPoints[_this.timeTrackPoints.length - 1]
    // 判断是否重复点
    if (last.lng === pointNew.lng && last.lat === pointNew.lat) {
      return
    }
  }
  _this.timeTrackPoints = [..._this.timeTrackPoints, ...[pointNew]] // 所有点

  var icon = _this.gmapUtils.createIcon({
    url: _this.positionInfo.productFileUrl || _this.markerIconOptions.url,
    scaledSize: { width: _this.markerIconOptions.size.width, height: '100%' },
    size: this.markerIconOptions.size,
    origin: this.markerIconOptions.origin
  })
  // 谷歌需要先清覆盖物 infowindow, marker
  if (_this.infowindow) {
    const infowindow = _this.infowindow
    _this.gmapUtils.closeInfoWindow({ infowindow })
  }
  // 先清-再打点，划线
  _this.markerClusterer && _this.markerClusterer.clearMarkers()
  _this.marker && _this.marker.setMap(null)
  _this.polyline && _this.polyline.setMap(null)
  _this.marker = _this.gmapUtils.createMarker({
    icon,
    point: pointNew,
    offset: { x: 0, y: 0 }
  })
  // 设置居中
  if (_this.timeTrackPoints.length > 2) {
    const options = {
      path: _this.timeTrackPoints
    }
    _this.gmapUtils.setFitView(options)
  }

  // 渲染地图marker点击后弹出的信息窗口
  const infowindowOptions = {
    content: '<div id="parent"><div></div></div>',
    minWidth: _this.infowindowOptions.width + 24, // 信息窗口宽度 +24和百度一样width
    maxWidth: _this.infowindowOptions.width + 24, // 信息窗口宽度
    offset: {
      width: 0, height: 10
    },
    title: '' // 信息窗口标题
  }
  _this.infowindow = _this.gmapUtils.createInfoWindow(infowindowOptions)

  _this.gmapUtils.addOverlay({ overlay: _this.marker })
  gmapDrawLine(_this)

  const openInfoWindowOptions = {
    infowindow: _this.infowindow,
    marker: _this.marker // 跟随marker
  }
  _this.gmapUtils.openInfoWindow(openInfoWindowOptions)
  // 处理infowindow数据加载
  setTimeout(() => {
    _this.getVue(false, pointNew)
  }, 20)
  _this.marker.addListener('click', () => {
    _this.gmapUtils.openInfoWindow(openInfoWindowOptions)
    _this.getVue(false, pointNew)
  })
}

function bmapDrawLine (_this) {
  const options = {
    ..._this.overlayOptions,
    path: _this.timeTrackPoints
  }
  const polyline = _this.bmapUtils.createPolyline(options)
  _this.bmapUtils.addOverlay({ overlay: polyline })
}
function gmapDrawLine (_this) {
  const options = {
    ..._this.overlayOptions,
    path: _this.timeTrackPoints
  }
  _this.polyline = _this.gmapUtils.createPolyline(options)
  _this.gmapUtils.addOverlay({ overlay: _this.polyline })
}

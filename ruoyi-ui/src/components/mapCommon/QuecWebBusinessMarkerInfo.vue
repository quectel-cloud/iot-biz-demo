<template>
  <div>
    <QuecWebAdvancedMap
      :type="type"
      :ak="ak"
      :lang="lang"
      :min-zoom="minZoom"
      :width="width"
      :height="height"
      @onready="onready"
    />
  </div>
</template>

<script>

import { QuecWebAdvancedMap } from 'quec-web-advanced-map'
import { getInfo } from "@/api/deviceInfo";
import {
  getVue,
  timeTrack
} from '@/utils/mapUtil'
/** *
 * 多种地图可同时存在
 */
export default {
  name: 'QuecWebBusinessClusterer',
  components: {
    QuecWebAdvancedMap
  },
  props: {
    // map类型
    type: {
      type: String,
      default: 'bmap'
    },
    // 秘钥
    ak: {
      type: String,
      default: 'pPurcozpqQVKnZg8NTG2peBYbxVZj5rx'
    },
    lang: {
      type: String,
      default: 'zh-CN'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    },
    center: {
      type: Object,
      default () {
        return { lng: 116.404, lat: 39.915 }
      }
    },

    // (国际化所需对应文字)
    resourceTxt: {
      type: Object,
      default () {
        return {}
      }
    },
    commonResourceTxt: {
      type: Object,
      default () {
        return {}
      }
    },
    // 设备：在线/离线(国际化)
    onlineStatusTxt: {
      type: Object,
      default () {
        return {
          online: '',
          offline: ''
        }
      }
    },
    // marker默认图
    markerIconOptions: {
      type: Object,
      default () {
        return {
          url: require('@/assets/images/map/markerDefault.png'),
          size: {
            width: 41,
            height: 41
          },
          origin: { x: 0, y: 0 }
        }
      }
    },
    // infowindow参数
    infowindowOptions: {
      type: Object,
      default () {
        return {
          width: 290,
          height: 290,
          // 信息框内容
          content: `
            <div class="map-msg">
              <div>
                <ul>
                  <li>
                    <span>设备号(IMEI)：</span>
                    <label v-if="positionInfo.imei">{{positionInfo.imei}}</label>
                    <label v-else>--</label>
                  </li>
                  <li>
                    <span>状态：</span>
                    <label v-if="positionInfo.onlineStatus === 0 || positionInfo.onlineStatus === 1">
                      {{positionInfo.onlineStatus === 0 ? '设备离线' : '设备在线'}}
                    </label>
                    <label v-else>--</label>
                  </li>
                  <li>
                    <span>SOC：</span>
                    <label v-if="positionInfo.soc">{{positionInfo.soc}}%</label>
                    <label v-else>--</label>
                  </li>
                  <li>
                    <span>经纬度：</span>
                    <label type="text" v-if="(positionInfo.lng || positionInfo.lng===0) && (positionInfo.lat || positionInfo.lat ===0)">
                      [{{positionInfo.lng}},{{positionInfo.lat}}]
                    </label>
                    <label v-else>--</label>
                  </li>
                  <li>
                    <span>定位地点：</span>
                    <i class="hand el-button--text" v-if="!locationNameShow" @click="getAddressByGeocoder(true)">查看地址</i>
                    <label v-else :title="locationName">{{locationName}}</label>
                  </li>
                  <li>
                    <span>定位时间：</span>
                    <label v-if="positionInfo.tsLocateTime">{{positionInfo.tsLocateTime}}</label>
                    <label v-else>--</label>
                  </li>
                  <li>
                    <span>定位方式：</span>
                    <label>{{positionInfo.locateType}}</label>
                  </li>
                  <li >
                    <span>信号强度：</span>
                    <label>{{positionInfo.hdop}}</label>
                  <li >
                    <span>当前卫星数：</span>
                    <label>{{positionInfo.satellites}}</label>
                  </li>
                </ul>
              </div>
            </div>
            `
        }
      }
    },
    // 接口所需参数（必传）
    paramsMarkerInfo: {
      type: Object,
      default () {
        return {
          deviceId: null
        }
      }
    },
    // infowindow展示的数据
    mapData: {
      type: Object,
      default () {
        return null
      }
    },
    // 按钮权限
    permission: {
      type: Object,
      default () {
        return {}
      }
    },
    // 覆盖物样式
    overlayOptions: {
      type: Object,
      default () {
        return {
          strokeColor: '#3893fe', // 设置矢量图标的线填充颜色
          // borderColor: '#939393',
          strokeWeight: 6, // 设置线宽
          strokeOpacity: 0.8 || 1
        }
      }
    }
  },
  data () {
    return {
      // 三大地图--封装对象
      bmapUtils: null,
      amapUtils: null,
      gmapUtils: null,
      // 获取单点位置信息
      locationPoint: null,
      gc: null,

      // 获取当前设备-对应数据
      positionInfo: {},
      // 谷歌
      minZoom: 2,
      infowindow: null,
      marker: null,
      polyline: null,
      // 实时跟踪
      // 所有点
      timeTrackPoints: []

    }
  },
  created () {

  },
  mounted () {
    this.positionInfo = this.mapData || this.positionInfo
  },
  destroyed () {
    clearInterval(this.timer)
  },
  methods: {
    // vue组件
    getVue (position) {
      const _this = this
      getVue(_this, position)
    },
    getAddressByGeocoder (callBack) {
      let utils = this.bmapUtils || this.amapUtils || this.gmapUtils
      let locationName = ''
      let options = {
        point: this.positionInfo
      }
      utils.getAddressByGeocoder(options, (callbackLocationName) => {
        if (utils._is.BMap) {
          locationName = callbackLocationName.address
        } else if (utils._is.GMap) {
          locationName = callbackLocationName[0].formatted_address
        }
        callBack && callBack(locationName)
      })
    },

    // 获取当前设备-对应数据
    getPositionInfo (deviceId, callback) {
      getInfo({ deviceId }).then(res => {
        if (res.code === 200) {
          this.positionInfo = res.data
          const data = res.data
          // 定位方式
          if (data.locateType || data.locateType === 0) {
            if (data.locateType === 0) {
              this.positionInfo.locateType = 'GNNS'
            } else if (data.locateType === 1) {
              this.positionInfo.locateType = 'LBS'
            } else {
              this.positionInfo.locateType = '--'
            }
          } else {
            this.positionInfo.locateType = '--'
          }
          if (data.locationType) {
            if (data.locationType === 1) {
              this.positionInfo.locationType = 'GPS'
            } else if (data.locationType === 2) {
              this.positionInfo.locationType = 'LBS'
            } else if (data.locationType === 3) {
              this.positionInfo.locationType = 'WIFI'
            } else {
              this.positionInfo.locationType = '--'
            }
          } else {
            this.positionInfo.locationType = '--'
          }
          // 处理三大地图经纬度
          if (this.type === 'bmap') {
            this.positionInfo.lng = res.data.wgsLng || res.data.lng
            this.positionInfo.lat = res.data.wgsLat || res.data.lat
          } else if (this.type === 'amap') {
            this.positionInfo.lng = res.data.gcjLng
            this.positionInfo.lat = res.data.gcjLat
          } else if (this.type === 'google') {
            this.positionInfo.lng = res.data.wgsLng
            this.positionInfo.lat = res.data.wgsLat
          }
          // 传给调用者
          this.$emit('getPositionInfo', this.positionInfo)
          callback && callback(res.data)
        }
      }).catch(() => {

      })
    },
    onready ({ map, XMap, utils }) {
      this.$emit('onready', { map, XMap, utils })
      if (this.paramsMarkerInfo.deviceId) {
        this.getPositionInfo(this.paramsMarkerInfo.deviceId, () => {
          this.mapOnready({ map, XMap, utils })
        })
      } else {
        this.mapOnready({ map, XMap, utils })
      }
    },
    mapOnready ({ map, XMap, utils }) {
      if (this.type === 'bmap') {
        this.bmapOnready({ map, XMap, utils })
      } else if (this.type === 'amap') {
        this.amapOnready({ map, XMap, utils })
      } else if (this.type === 'google') {
        this.gmapOnready({ map, XMap, utils })
      }
    },
    bmapOnready ({ map, XMap, utils }) {
      const _this = this
      _this.bmapUtils = utils
      _this.primaryBmap = XMap
      // marker
      const center = { lng: _this.positionInfo.lng, lat: _this.positionInfo.lat }
      const point = utils.createPoint(center)
      const icon = utils.createIcon({
        url: _this.positionInfo.productFileUrl || _this.markerIconOptions.url,
        size: _this.markerIconOptions.size
      })
      const marker = utils.createMarker({
        icon,
        point,
        offset: { x: 0, y: 0 }
      })
      // 渲染地图marker点击后弹出的信息窗口
      const options = {
        center,
        zoom: 19
      }
      const infowindowOptions =
      {
        content: '<div id="parent"><div></div></div>',
        width: _this.infowindowOptions.width, // 信息窗口宽度
        height: _this.infowindowOptions.height, // 信息窗口高度
        offset: {
          width: -32, height: -5
        },
        title: '', // 信息窗口标题
        enableMessage: true // 设置允许信息窗发送短息
      }
      const infowindow = utils.createInfoWindow(infowindowOptions)
      const openInfoWindowOptions = {
        infowindow,
        point
      }
      utils.setZoomAndCenter(options)
      utils.addOverlay({ overlay: marker })
      utils.openInfoWindow(openInfoWindowOptions)

      // 处理infowindow数据加载
      utils.getAddressByGeocoder({ point: center }, (callbackLocationName) => {
        utils.setZoomAndCenter(options) // 解决刷新页面：百度地图infowindow小偏移问题
        _this.getVue()
      })
      marker.addEventListener('click', () => {
        utils.openInfoWindow(openInfoWindowOptions)
        _this.getVue()
      })
    },
    amapOnready ({ utils }) {
    },
    gmapOnready ({ map, XMap, utils }) {
      const _this = this
      _this.gmapUtils = utils
      _this.primaryGmap = map
      // marker
      const center = { lng: _this.positionInfo.lng, lat: _this.positionInfo.lat }
      var icon = utils.createIcon({
        url: _this.positionInfo.productFileUrl || _this.markerIconOptions.url,
        scaledSize: { width: _this.markerIconOptions.size.width, height: '100%' },
        size: _this.markerIconOptions.size,
        origin: _this.markerIconOptions.origin
      })
      _this.marker = utils.createMarker({
        icon,
        point: center,
        offset: { x: 0, y: 0 }
      })
      // 渲染地图marker点击后弹出的信息窗口
      const infowindowOptions =
      {
        content: '<div id="parent"><div></div></div>',
        minWidth: _this.infowindowOptions.width + 24, // 信息窗口宽度 +24和百度一样width
        maxWidth: _this.infowindowOptions.width + 24, // 信息窗口宽度
        offset: {
          width: -1, height: 10
        },
        title: '' // 信息窗口标题
      }
      _this.infowindow = utils.createInfoWindow(infowindowOptions)
      const openInfoWindowOptions = {
        infowindow: _this.infowindow,
        marker: _this.marker // 跟随marker
      }

      const options = {
        center,
        zoom: 19
      }
      utils.setZoomAndCenter(options)
      utils.addOverlay({ overlay: _this.marker })
      utils.openInfoWindow(openInfoWindowOptions)
      // 处理infowindow数据加载
      utils.getAddressByGeocoder(center, (callbackLocationName) => {
        _this.getVue()
      })

      _this.marker.addListener('click', () => {
        utils.openInfoWindow(openInfoWindowOptions)
        _this.getVue()
      })
    },

    // 实时定位
    timeTrack (options = {}, callBack) {
      const _this = this
      timeTrack(_this, options, callBack)
    }

  }
}
</script>

<style scoped></style>

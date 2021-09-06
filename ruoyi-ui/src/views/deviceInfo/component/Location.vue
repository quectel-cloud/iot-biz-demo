<template>
  <div class="map-box">
    <div v-show="mapShow">
      <QuecWebBusinessMarkerInfo
        ref="map"
        class="mapContainer"
        :type="bmapType"
        :ak="bmapAk"
        :params-marker-info="paramsMarkerInfo"
        :markerIconOptions="markerIconOptions"
        :infowindowOptions="infowindowOptions"
        height="630px"
        @getPositionInfo="getPositionInfo"
      />
    </div>
    <div class="flex-center detail-content" v-show="!mapShow">
      <div class="tc no-data">
        <img :src="noDataSrc" alt="" />
        <p>暂无数据</p>
      </div>
    </div>
  </div>
</template>

<script>
import { filterLocationType } from '@/filters/index'
import QuecWebBusinessMarkerInfo from '@/components/mapCommon/QuecWebBusinessMarkerInfo'

export default {
  components: {
    QuecWebBusinessMarkerInfo
  },
  data () {
    return {
      mapShow: true,
      noDataSrc: require('@/assets/images/no-data.png'),
      deviceDtail: localStorage.getItem('deviceDtail'),
      activeName: 'Fault',

      // 组件化data
      bmapType: 'bmap',
      bmapAk: 'b7gExp9cl2yK6tflvcThbFUGW8cObjA4',

      // marker默认图
      markerIconOptions: {
        url: require('@/assets/images/map/markerDefault.png'),
        size: {
          width: 41,
          height: 41,
          origin: { x: 0, y: 0 }
        }
      },
      // infowindow参数
      infowindowOptions: {
        width: 290, // 信息窗口宽度
        height: 260, // 信息窗口高度
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
                    <label v-if="positionInfo.locateTime">{{positionInfo.locateTime}}</label>
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
      },
      // 接口所需参数（必传）
      paramsMarkerInfo: {
        deviceId: null
      }
    }
  },
  watch: {
  },
  created () {
    // 切换语言需要重载谷歌js
    document.querySelectorAll('script[src^="https://maps.googleapis.com"]').forEach(function (script) {
      script.remove()
    })
    if (window.google) {
      delete window.google
    }
  },
  mounted () {
    this.paramsMarkerInfo = {
      deviceId: 1
    }
  },
  destroyed () {
    const _this = this
    clearInterval(_this.timer)
  },
  methods: {
    filterLocationType,
    // Device组件数据
    getPositionInfo (positionInfo) {
      // this.positionInfo = positionInfo
      this.mapShow = positionInfo.lng && positionInfo.lat
    }

  }
}

</script>

<style lang="scss" scoped>
#mapContainer {
  height: 600px;
  margin: 0 auto;
}
::v-deep {
  .el-tabs__header,
  .el-tabs__content {
    padding: 0 !important;
  }
  .el-dialog__body {
    padding-top: 0;
  }
  .detail-content {
    padding: 16px 0;
  }
  .pagination-container {
    padding-bottom: 0;
  }
}
</style>

<template>
  <div class="app-container">
    <!-- 基本信息 -->
    <baseInfo :deviceDtail="deviceDtail"></baseInfo>
    <!-- 传感器数值 -->
    <propertiesInfo :sensorData="sensorData"></propertiesInfo>
    <el-tabs v-model="activeName" class="detail-tabs">
      <el-tab-pane label="位置信息" name="Location" lazy>
        <Location v-if="activeName === 'Location'" ref="Location" />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { getInfo, getProperties } from "@/api/deviceInfo";
import baseInfo from './component/baseInfo'
import propertiesInfo from './component/propertiesInfo'
import Location from './component/Location'
export default {
  components: {
    baseInfo,
    propertiesInfo,
    Location
  },
  data () {
    return {
      deviceDtail: undefined,
      sensorData: undefined,
      activeName: 'Location'
    }
  },
  methods: {
    getDeviceInfo () {
      getInfo().then(res => {
        this.deviceDtail = res.data
      })
      getProperties().then(res => {
        this.sensorData = res.data
      })
    }
  },
  created () {
    this.getDeviceInfo()
  }
}
</script>

<style>
</style>
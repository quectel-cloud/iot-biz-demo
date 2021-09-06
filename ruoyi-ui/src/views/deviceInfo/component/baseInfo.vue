<template>
  <div class="base-info">
    <el-descriptions :column="3" title="基本信息" border v-if="deviceDtail">
      <el-descriptions-item label="设备IMEI">{{
        deviceDtail.imei
      }}</el-descriptions-item>
      <el-descriptions-item label="设备名称">{{
        deviceDtail.deviceName
      }}</el-descriptions-item>
      <el-descriptions-item label="设备SN">
        {{ deviceDtail.deviceSn }}
      </el-descriptions-item>
      <el-descriptions-item label="模组型号">
        {{ deviceDtail.modelSpec }}
      </el-descriptions-item>
      <el-descriptions-item label="在线状态">
        {{ deviceDtail.onlineStatus === 1 ? "在线" : "离线" }}
      </el-descriptions-item>
      <el-descriptions-item label="上线时间">
        {{ parseTime(deviceDtail.tsOnlineTime) }}
      </el-descriptions-item>
      <el-descriptions-item label="ICCID">
        {{ deviceDtail.iccid }}
      </el-descriptions-item>
      <el-descriptions-item label="SOC">
        {{ deviceDtail.soc }}%
      </el-descriptions-item>
    </el-descriptions>
  </div>
</template>

<script>
export default {
  props: {
    deviceDtail: {
      type: Object
    }
  },
}
</script>

<style>
</style>
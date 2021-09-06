<template>
  <div class="sensor">
    <div class="sensor-title">
      <span>设备属性</span>
      <div class="sensor-title-toggle" @click="showToggle">
        <template v-if="!showSwiper"
          >展开<i class="el-icon-bottom"></i
        ></template>
        <template v-else-if="showSwiper"
          >收起<i class="el-icon-top"></i
        ></template>
      </div>
    </div>
    <div class="sensor-content" v-if="sensorData">
      <div class="swiper" v-if="!showSwiper">
        <div
          v-for="(item, index) of sensorData.slice(0, 5)"
          :key="index"
          class="sensor-hidden"
        >
          <div class="sensor-content-item clearfix">
            <div class="sensor-content-item-icon vm">
              <svg-icon :icon-class="item.icon" v-if="item.icon" />
              <svg-icon icon-class="General" v-else />
            </div>
            <div class="sensor-content-item-text vm">
              <div class="item" v-if="item.dataType !== 'STRUCT'">
                <span class="text-o">{{ item.name }}</span>
                <h1
                  v-if="item.dataType === 'BOOL' || item.dataType === 'ENUM'"
                  class="text-o"
                >
                  {{ gDatasFormatter(item.upValue, item.specs) || "--" }}
                </h1>
                <h1 v-else class="text-o">
                  {{ item.upValue || "--" }}
                  <template v-if="item.upValue">{{ item.unit }}</template>
                </h1>
              </div>
              <swiper :options="swiperOption" ref="swiperOptionRef" v-else>
                <swiper-slide
                  v-for="(dataTtem, dataIndex) in item.specs"
                  :key="dataIndex"
                >
                  <div class="item">
                    <span class="text-o">{{ dataTtem.name }}</span>
                    <h1
                      v-if="
                        dataTtem.dataType === 'BOOL' ||
                        dataTtem.dataType === 'ENUM'
                      "
                      class="text-o"
                    >
                      {{
                        gDatasFormatter(dataTtem.upValue, dataTtem.specs) ||
                        "--"
                      }}
                    </h1>
                    <h1 v-else class="text-o">
                      {{ dataTtem.upValue || "--"
                      }}<template v-if="dataTtem.upValue">{{
                        dataTtem.unit
                      }}</template>
                    </h1>
                  </div>
                </swiper-slide>
                <div class="swiperOption-pagination" slot="pagination"></div>
              </swiper>
            </div>
          </div>
        </div>
      </div>
      <div class="swiper" v-if="showSwiper && sensorData.length <= 15">
        <div
          v-for="(item, index) of sensorData"
          :key="index"
          class="sensor-hidden"
        >
          <div class="sensor-content-item clearfix">
            <div class="sensor-content-item-icon vm">
              <svg-icon :icon-class="item.icon" v-if="item.icon" />
              <svg-icon icon-class="General" v-else />
            </div>
            <div class="sensor-content-item-text vm">
              <div class="item" v-if="item.dataType !== 'STRUCT'">
                <span class="text-o">{{ item.name }}</span>
                <h1
                  v-if="item.dataType === 'BOOL' || item.dataType === 'ENUM'"
                  class="text-o"
                >
                  {{ gDatasFormatter(item.upValue, item.specs) || "--" }}
                </h1>
                <h1 v-else class="text-o">
                  {{ item.upValue || "--" }}
                  <template v-if="item.upValue">{{ item.unit }}</template>
                </h1>
              </div>
              <swiper :options="swiperOption" ref="swiperOptionRef" v-else>
                <swiper-slide
                  v-for="(dataTtem, dataIndex) in item.specs"
                  :key="dataIndex"
                >
                  <div class="item">
                    <span class="text-o">{{ dataTtem.name }}</span>
                    <h1
                      v-if="
                        dataTtem.dataType === 'BOOL' ||
                        dataTtem.dataType === 'ENUM'
                      "
                      class="text-o"
                    >
                      {{
                        gDatasFormatter(dataTtem.upValue, dataTtem.specs) ||
                        "--"
                      }}
                    </h1>
                    <h1 v-else class="text-o">
                      {{ dataTtem.upValue || "--" }}
                      <template v-if="dataTtem.upValue">{{
                        dataTtem.unit
                      }}</template>
                    </h1>
                  </div>
                </swiper-slide>
                <div class="swiperOption-pagination" slot="pagination"></div>
              </swiper>
            </div>
          </div>
        </div>
      </div>
      <div v-if="showSwiper && sensorData.length > 15">
        <swiper
          ref="swiperOptionWrapRef"
          :options="swiperOptionWrap"
          class="swiper"
        >
          <swiper-slide v-for="(item, index) of sensorData" :key="index">
            <div class="sensor-content-item clearfix">
              <div class="sensor-content-item-icon vm">
                <svg-icon :icon-class="item.icon" v-if="item.icon" />
                <svg-icon icon-class="General" v-else />
              </div>
              <div class="sensor-content-item-text vm">
                <div class="item" v-if="item.dataType !== 'STRUCT'">
                  <span class="text-o">{{ item.name }}</span>
                  <h1
                    v-if="item.dataType === 'BOOL' || item.dataType === 'ENUM'"
                    class="text-o"
                  >
                    {{ gDatasFormatter(item.upValue, item.specs) || "--" }}
                  </h1>
                  <h1 v-else class="text-o">
                    {{ item.upValue || "--" }}
                    <template v-if="item.upValue">{{ item.unit }}</template>
                  </h1>
                </div>
                <swiper :options="swiperOption" ref="swiperOptionRef" v-else>
                  <swiper-slide
                    v-for="(dataTtem, dataIndex) in item.specs"
                    :key="dataIndex"
                  >
                    <div class="item">
                      <span class="text-o">{{ dataTtem.name }}</span>
                      <h1
                        v-if="
                          dataTtem.dataType === 'BOOL' ||
                          dataTtem.dataType === 'ENUM'
                        "
                        class="text-o"
                      >
                        {{
                          gDatasFormatter(dataTtem.upValue, dataTtem.specs) ||
                          "--"
                        }}
                      </h1>
                      <h1 v-else class="text-o">
                        {{ dataTtem.upValue || "--" }}
                        <template v-if="dataTtem.upValue">{{
                          dataTtem.unit
                        }}</template>
                      </h1>
                    </div>
                  </swiper-slide>
                  <div class="swiperOption-pagination" slot="pagination"></div>
                </swiper>
              </div>
            </div>
          </swiper-slide>
        </swiper>
        <div class="el-icon-arrow-left" slot="button-prev"></div>
        <div class="el-icon-arrow-right" slot="button-next"></div>
      </div>
    </div>
  </div>
</template>
<script>

import 'swiper/swiper-bundle.css'
import VueAwesomeSwiper from 'vue-awesome-swiper'
export default {
  props: {
    sensorData: {
      type: Array
    }
  },
  components: {
    VueAwesomeSwiper
  },
  data () {
    return {
      showSwiper: false,
      swiperOptionWrap: {
        slidesPerView: 5,
        slidesPerColumn: 3,
        slidesPerColumnFill: 'row',
        spaceBetween: 0,
        loop: false,
        autoplay: false,
        observer: true,
        // 设置点击箭头
        navigation: {
          nextEl: '.el-icon-arrow-right',
          prevEl: '.el-icon-arrow-left'
        }
      },
      swiperOption: {
        slidesPerView: 1,
        spaceBetween: 0,
        pagination: {
          el: '.swiperOption-pagination',
          clickable: true
        },
        simulateTouch: false
      },
    }
  },
  methods: {
    // 展示隐藏设备属性
    showToggle () {
      this.showSwiper = !this.showSwiper
    },
    // 字典翻译
    gDatasFormatter (value, dicData) {
      let results = ''
      if (dicData && dicData.length > 0) {
        for (let i = 0; i < dicData.length; i++) {
          let item = dicData[i]
          if (JSON.parse(value) === JSON.parse(item.value)) {
            results = item.name
            break
          }
        }
      }
      return results
    },
  }
}
</script>
<style lang="scss" scoped>
.swiper {
  width: 100%;
}
.sensor {
  margin-top: 30px;
  &-title {
    border-bottom: 1px solid #e4e7ee;
    span {
      display: inline-block;
      background-color: #f6f9ff;
      border-radius: 0px 44px 0px 0px;
      font-size: 12px;
      padding: 6px 26px 6px 11px;
      color: #999999;
    }
    &-toggle {
      color: #456ff7;
      font-size: 14px;
      float: right;
      cursor: pointer;
      margin-top: 5px;
    }
  }
  &-content {
    // display: flex;
    // justify-content: space-between;
    padding: 0 60px;
    margin: 40px 0 -20px;
    position: relative;
    --swiper-navigation-size: 30px; /* 设置按钮大小 */

    &-item {
      height: 98px;
      margin-bottom: 30px;
      // display: flex;
      // align-items: center;
      &-icon {
        width: 54px;
        height: 54px;
        .svg-icon {
          width: 54px;
          height: 54px;
        }
      }
      &-text {
        // flex: 1;
        width: calc(100% - 54px - 20px);
        margin-left: 20px;
        span {
          color: #9097ab;
          font-size: 12px;
        }
        h1 {
          color: #333333;
          font-size: 24px;
          margin: 10px 0;
        }
      }
    }
    .el-icon-arrow {
      &-left,
      &-right {
        position: absolute;
        top: 50%;
        margin-top: -21px;
        z-index: 9;
        cursor: pointer;
        width: 32px;
        height: 42px;
        line-height: 42px;
        text-align: center;
        transform: translateY(-100%);
        margin: 0;
        color: #bbc7d5;
        font-size: 36px;
      }
      &-left {
        left: 0;
      }
      &-right {
        right: 0;
      }
    }
  }
}
.sensor-hidden {
  .sensor-content-item {
    float: left;
    width: 20%;
  }
}
</style>

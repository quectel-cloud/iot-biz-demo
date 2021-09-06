import Vue from 'vue'
import SvgIcon from '@/components/SvgIcon'// svg component

// register globally
Vue.component('svg-icon', SvgIcon)

const req = require.context('./svg', false, /\.svg$/)
const reqProp = require.context('./svgProp', false, /\.svg$/)
const requireAll = requireContext => requireContext.keys().map(requireContext)
const requireProp = requireContext => requireContext.keys().map(requireContext)
requireAll(req)
requireProp(reqProp)
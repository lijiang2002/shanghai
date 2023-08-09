
<template>
  <n-config-provider :locale="zhCN"  :dateLocale = "dateZhCN" :theme-overrides="themeOverrides">
    <n-message-provider>
      <router-view/>
    </n-message-provider>
  </n-config-provider>
</template>
<script setup lang="ts">
import { zhCN, dateZhCN } from 'naive-ui'
import  themeOverrides from '@/theme'
</script>

<style scoped>

</style>

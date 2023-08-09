<template>
  <n-space style="width:1180px;margin: 25px auto" vertical>
    <n-card title="艺蕾音乐" bordered>
      <n-tabs type="line" animated>
        <n-tab-pane name="音乐模拟考试" tab="音乐模拟考试">
          <examination/>
        </n-tab-pane>
        <n-tab-pane name="学生" tab="学生">
          <student/>
        </n-tab-pane>
        <n-tab-pane v-if="use_Store.user.username=='admin'" name="老师" tab="老师">
          <teacher/>
        </n-tab-pane>
        <n-tab-pane  name="图片上传" tab="图片上传">
          <upload/>
        </n-tab-pane>
      </n-tabs>
    </n-card>
  </n-space>
</template>

<script lang="ts">
import {defineComponent, onMounted} from "vue";
import examination from "@/components/examination.vue"
import student from "@/components/student.vue"
import teacher from "@/components/teacher.vue"
import upload from "@/components/upload.vue"
import useStore from "@/store/modules/useStore";
import {useRouter} from "vue-router";
import {useMessage} from "naive-ui";


export default defineComponent({
  components: {
    examination,
    student,
    teacher,
    upload,
  },
  setup() {
    onMounted(() => {
      if (use_Store.user.username == null || use_Store.user.username == '') {
        message.error("请登录")
        router.push("/login")
      }
    })
    const message = useMessage()
    const router = useRouter()
    const use_Store = useStore()
    return {
      use_Store
    }
  }
})

</script>
<style scoped>

</style>

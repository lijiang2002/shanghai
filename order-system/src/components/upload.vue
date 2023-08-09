<template>
  <n-space vertical>
    <n-upload
        :action="url+'/upload'"
        multiple
        :show-file-list="false"
        directory-dnd
        @finish="getList"
    >
      <n-upload-dragger>

        <n-text style="font-size: 16px">
          点击或者拖动文件到该区域来上传
        </n-text>

      </n-upload-dragger>
    </n-upload>
    <n-space>
      <n-space vertical align="center" v-for="(item) in fileList">
        <n-image
            width="100"
            height="100"
            :src="item.url"
            object-fit="contain"
        />
        <n-button type="error" size="small" @click="del(item.id)">删除</n-button>
      </n-space>
    </n-space>
  </n-space>
</template>

<script lang="ts">
import {defineComponent, onMounted, ref} from 'vue'
import {UploadFileInfo} from 'naive-ui'
import {getListApi, putApi} from "@/api/fileApi";
import {url} from "@/main";


export default defineComponent({
  setup() {
    onMounted(() => {
      getList()
    })
    const getList = () => {
      getListApi().then((res: any) => {
        for (let i = 0; i < res.data.length; i++) {
          res.data[i].url = url + "/img/" + res.data[i].name
          res.data[i].status = 'finished'
        }
        fileList.value = res.data
      })
    }
    const fileList = ref<UploadFileInfo[]>()
    const del = (id: any) => {
      putApi(id).then(() => {
        getList()
      })
    }
    return {
      fileList,
      url,
      beforeUpload: (a: any) => {
        console.log(a)
        return a
      },
      del,
      getList
    }

  }
})
</script>

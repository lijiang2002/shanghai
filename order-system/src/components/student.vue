<template>
  <n-space vertical>
    <n-input placeholder="搜索（输入名称、身份证）" v-model:value="sous" @input="getList"/>
    <n-data-table
        :columns="columns"
        :data="data"
        :pagination="pagination"
        :bordered="false"
        :remote="true"
    />
  </n-space>

  <n-modal
      v-model:show="showModal"
      class="custom-card"
      preset="card"
      title="艺蕾音乐模拟考试"
      size="huge"
      :bordered="false"
      style="width: 980px;border-radius: 10px"
  >
    <n-row>
      <n-col :span="8">
        <n-statistic label="考生姓名">
          <template #suffix>
            {{ student.name }}
          </template>
        </n-statistic>
      </n-col>
      <n-col :span="16">
        <n-statistic label="身份证号" style="font-size: 20px !important;">
          {{ maskString(student.identity) }}
        </n-statistic>
      </n-col>
    </n-row>
    <n-p/>
    <n-p/>
    <n-p/>
    <n-list bordered hoverable>
      <template #header>
        考试记录
      </template>
      <template #footer>

      </template>
      <n-list-item v-for="(item) in student.examinations">
        <h2>
         <n-space align="center">
           <n-time :time="item.cdate"/>
           <n-tag type="error" v-if="(item.s1==null||item.s1==''||item.s1==0)">未考试</n-tag>
         </n-space>
        </h2>
        <n-thing title="声乐" :title-extra="'系统选定考试曲目：'+item.sy4">
          <n-space v-if="!(item.s1==null||item.s1==''||item.s1==0)">
            <n-tag type="success">
              分值1：{{ item.s1 }}
            </n-tag>
            <n-tag type="success">
              分值1：{{ item.s2 }}
            </n-tag>
            <n-tag type="success">
              分值1：{{ item.s3 }}
            </n-tag>
            <n-tag type="success">
              分值1：{{ item.s4 }}
            </n-tag>
            <n-tag type="success">
              分值1：{{ item.s5 }}
            </n-tag>
          </n-space>
          <n-blockquote>
            <n-text prefix="bar" style="font-size: 14px !important;color: #919191">
              预选（{{ item.sy1 }}、{{ item.sy2 }}、{{ item.sy3 }}）
            </n-text>
          </n-blockquote>
        </n-thing>
        <n-hr/>
        <n-thing title="器乐" :title-extra="'系统选定考试曲目：'+item.qy4">
          <n-space v-if="!(item.s1==null||item.s1==''||item.s1==0)">
            <n-tag type="success">
              分值1：{{ item.q1 }}
            </n-tag>
            <n-tag type="success">
              分值1：{{ item.q2 }}
            </n-tag>
            <n-tag type="success">
              分值1：{{ item.q3 }}
            </n-tag>
            <n-tag type="success">
              分值1：{{ item.q4 }}
            </n-tag>
            <n-tag type="success">
              分值1：{{ item.q5 }}
            </n-tag>
          </n-space>
          <n-blockquote>
            <n-text prefix="bar" style="font-size: 14px !important;color: #919191">
              预选（{{ item.qy1 }}、{{ item.qy2 }}、{{ item.qy3 }}）
            </n-text>
          </n-blockquote>
        </n-thing>
      </n-list-item>


    </n-list>
  </n-modal>
  <n-modal
      v-model:show="updateShowModal"
      class="custom-card"
      preset="card"
      title="修改操作"
      size="huge"
      :bordered="false"
      style="width: 500px;border-radius: 10px"
  >
    <n-space vertical>
      <n-form
          ref="updateFormRef"
          :label-width="80"
          :model="updateFormValue"
          :rules="updateRules"

      >
        <n-form-item label="用户名" path="name">
          <n-input type="text" v-model:value="updateFormValue.name" placeholder="请输入"/>
        </n-form-item>
        <n-form-item label="用户身份证" path="identity">
          <n-input type="text" v-model:value="updateFormValue.identity" placeholder="请输入"/>
        </n-form-item>
      </n-form>
      <n-button type="success" block @click="updateHandleValidateButtonClick">
        修改
      </n-button>
    </n-space>

  </n-modal>
</template>

<script lang="ts">
import {h, defineComponent, ref, onMounted} from 'vue'
import {FormInst, FormItemRule, NButton, NSpace, useMessage} from 'naive-ui'
import type {DataTableColumns} from 'naive-ui'
import {getListApi, deleteApi, putApi, getApi} from "@/api/studentApi";


type Song = {
  name: number
  identity: string
}


export default defineComponent({
  setup() {
    onMounted(() => {
      getList()
    })
    const message = useMessage()
    const showModal = ref(false)
    const maskString = (input:any) => {
      if (typeof input !== "string" || input.length < 8) {
        return "Invalid input";
      }
      const maskedInput = input.substring(0, input.length - 4) + "****";
      return maskedInput;
    }
    const createColumns = (): DataTableColumns<Song> => {
      return [
        {
          title: '名字',
          key: 'name'
        },
        {
          title: '身份证号',
          key: 'identity',
          render(row) {
            return maskString(row.identity)
          }
        },
        {
          title: '操作',
          key: 'actions',
          render(row) {
            return h(NSpace, {}, [h(
                    NButton,
                    {
                      strong: true,
                      tertiary: true,
                      type: 'success',
                      size: 'small',
                      onClick: () => {
                        getApi(row.identity).then((res: any) => {
                          student.value = res.data
                          showModal.value = true
                        })
                      }
                    },
                    {default: () => '详情'}
                ), h(
                    NButton,
                    {
                      strong: true,
                      tertiary: true,
                      type: 'info',
                      size: 'small',
                      onClick: () => {
                        updateShowModal.value = true
                        updateFormValue.value = row as any
                      }
                    },
                    {default: () => '修改'}
                ), h(
                    NButton,
                    {
                      strong: true,
                      tertiary: true,
                      type: 'error',
                      size: 'small',
                      onClick: () => {
                        deleteApi(row.identity).then(() => {
                          getList()
                        })
                      }
                    },
                    {default: () => '删除'}
                )]
            )
          }
        }
      ]
    }
    const student = ref<any>(null)
    const pagination = ref({
      page: 2,
      pageSize: 5,
      showSizePicker: true,
      pageSizes: [3, 5, 7, 10],
      itemCount: 1,
      pageCount: 1,
      showQuickJumper: true,
      onChange: (page: number) => {
        pagination.value.page = page
        getList()
      },
      onUpdatePageSize: (pageSize: number) => {
        pagination.value.pageSize = pageSize
        pagination.value.page = 1
        getList()
      }
    })
    const sous = ref('')
    const data = ref([])
    const getList = () => {
      getListApi(pagination.value, sous.value).then((res: any) => {
        data.value = res.data.records
        pagination.value.page = res.data.current
        pagination.value.itemCount = res.data.total
        pagination.value.pageCount = res.data.pages
      })
    }
    const updateFormValue = ref({
      id: null,
      name: null,
      identity: null
    })
    const updateRules = {
      name:
          {
            required: true,
            validator(rule: FormItemRule, value: string) {
               if (!value) {
                return new Error('不能为空')
              }console.log(rule)
              return true
            },
            trigger: ['input', 'blur']
          },
      identity: {
        required: true,
        validator(rule: FormItemRule, value: string) {
          console.log(rule)
          if (!value) {
            return new Error('不能为空')
          }
          return true
        },
        trigger: ['input', 'blur']
      }
    }
    const updateFormRef =  ref<FormInst | null>(null)
    const updateShowModal = ref(false)
    const updateHandleValidateButtonClick = (e: MouseEvent) => {
      e.preventDefault()
      updateFormRef.value?.validate((errors) => {
        if (!errors) {
          putApi(updateFormValue.value).then((res: any) => {
            console.log(res)
            getList()
            updateShowModal.value = false
          })
        } else {
          message.error('验证失败')
        }
      })
    }
    return {
      data,
      showModal,
      columns: createColumns(),
      maskString,
      pagination,
      sous,
      getList,
      updateFormValue,
      updateRules,
      updateFormRef,
      updateShowModal,
      updateHandleValidateButtonClick,
      student
    }
  }
})

</script>
<style scoped>
.carousel-img {
  width: 100%;
  height: 160px;
  object-fit: cover;
  border-radius: 6px;
}
</style>

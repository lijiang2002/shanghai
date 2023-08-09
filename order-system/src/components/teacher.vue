<template>
  <n-space vertical>
    <n-input placeholder="搜索（输入名称）" v-model:value="sousValue" @input="getList"/>
    <n-button type="success" @click="showModal=true" block>
      添加老师
    </n-button>
    <n-data-table
        :columns="columns"
        :data="data"
        :pagination="pagination"
        :remote="true"
    />
  </n-space>

  <n-modal
      v-model:show="showModal"
      class="custom-card"
      preset="card"
      title="添加操作"
      size="huge"
      :bordered="false"
      style="width: 500px;border-radius: 10px"
  >
    <n-space vertical>
      <n-form
          ref="addFormRef"
          :label-width="80"
          :model="addFormValue"
          :rules="addRules"

      >
        <n-form-item label="用户名" path="username">
          <n-input type="text" v-model:value="addFormValue.username" placeholder="请输入"/>
        </n-form-item>
        <n-form-item label="用户密码" path="password">
          <n-input type="password" v-model:value="addFormValue.password" placeholder="请输入"/>
        </n-form-item>
      </n-form>
      <n-button type="success" block @click="addHandleValidateButtonClick">
        添加老师
      </n-button>
    </n-space>

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
        <n-form-item label="用户名" path="username">
          <n-input type="text" disabled v-model:value="updateFormValue.username" placeholder="请输入"/>
        </n-form-item>
        <n-form-item label="用户密码" path="password">
          <n-input type="password" v-model:value="updateFormValue.password" placeholder="请输入"/>
        </n-form-item>
      </n-form>
      <n-button type="success" block @click="updateHandleValidateButtonClick">
        新密码
      </n-button>
    </n-space>

  </n-modal>
</template>

<script lang="ts">
import {h, defineComponent, ref, onMounted} from 'vue'
import {FormInst, FormItemRule, NButton, NSpace, useMessage} from 'naive-ui'
import type {DataTableColumns} from 'naive-ui'
import {deleteApi, getListApi, saveApi, updateApi} from "@/api/teacherApi";


type Song = {
  username: string
  password: string
}
export default defineComponent({
  setup() {
    onMounted(() => {
      getList()
    })
    const message = useMessage()
    const showModal = ref(false)
    const updateShowModal = ref(false)
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
          title: '用户名',
          key: 'username'
        },
        {
          title: '密码',
          key: 'password',
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
                      type: 'info',
                      size: 'small',
                      onClick: () => {
                        updateFormValue.value.username = row.username as any
                        updateShowModal.value = true
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
                      disabled: row.username == "admin",
                      onClick: () => {
                        deleteApi(row.username);
                        getList()
                      }
                    },
                    {default: () => '删除'}
                )]
            )
          }
        }
      ]
    }
    const pagination = ref({
      page: 1,
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
    const data = ref<Song[]>([])
    const getList = () => {
      getListApi(pagination.value, sousValue.value).then((res: any) => {
        data.value = res.data.records
        pagination.value.page = res.data.current
        pagination.value.itemCount = res.data.total
        pagination.value.pageCount = res.data.pages
      })
    }
    const sousValue = ref<string | null>(null)
    const addFormValue = ref({
      username: null,
      password: null
    })
    const updateFormValue = ref({
      username: null,
      password: null
    })
    const addRules = {
      username:
          {
            required: true,
            validator(rule: FormItemRule, value: string) {
              console.log(rule)
              if (!value) {
                return new Error('不能为空')
              }
              return true
            },
            trigger: ['input', 'blur']
          },
      password: {
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
    const updateRules = {
      username:
          {
            required: true,
            validator(rule: FormItemRule, value: string) {
              console.log(rule)
              if (!value) {
                return new Error('不能为空')
              }
              return true
            },
            trigger: ['input', 'blur']
          },
      password: {
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
    const addFormRef =  ref<FormInst | null>(null)
    const updateFormRef =  ref<FormInst | null>(null)
    const addHandleValidateButtonClick = (e: MouseEvent) => {
      e.preventDefault()
      addFormRef.value?.validate((errors:any) => {
        if (!errors) {
          saveApi(addFormValue.value).then(() => {
            getList()
            showModal.value = false
            addFormValue.value.username = null
            addFormValue.value.password = null
          })
        } else {
          console.log(errors)
          message.error('验证失败')
        }
      })
    }
    const updateHandleValidateButtonClick = (e: MouseEvent) => {
      e.preventDefault()
      updateFormRef.value?.validate((errors:any) => {
        if (!errors) {
          updateApi(updateFormValue.value).then(() => {
            getList()
            updateShowModal.value = false
            updateFormValue.value.username = null
            updateFormValue.value.password = null
          })
        } else {
          console.log(errors)
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
      sousValue,
      getList,
      addFormValue,
      addRules,
      addFormRef,
      updateFormValue,
      updateRules,
      updateFormRef,
      addHandleValidateButtonClick,
      updateHandleValidateButtonClick,
      updateShowModal
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

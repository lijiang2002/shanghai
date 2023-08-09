<template>
  <div class="main">
    <div class="left">
    </div>
    <div class="right">
      <div>
        <h3>系统登录</h3>
        <n-form ref="formRef" :model="model" :rules="rules" style="width: 400px">
          <n-form-item path="username" label="用户名">
            <n-input v-model:value="model.username" placeholder="请输入用户名"/>
          </n-form-item>
          <n-form-item path="password" label="密码">
            <n-input
                placeholder="请输入密码"
                v-model:value="model.password"
                type="password"
            />
          </n-form-item>
          <n-row :gutter="[0, 24]">
            <n-col :span="24">
              <div style="display: flex; justify-content: flex-end">
                <n-button
                    :disabled="model.username === null"
                    type="primary"
                    @click="handleValidateButtonClick"
                    style="width: 100%"
                >
                  登录
                </n-button>
              </div>
            </n-col>
          </n-row>
        </n-form>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {defineComponent, ref} from 'vue'
import {useRouter} from 'vue-router'
import {
  FormInst,
  FormItemInst,
  useMessage,
  FormRules, FormItemRule
} from 'naive-ui'


import useStore from "@/store/modules/useStore"
import {teacherApi} from "@/api/teacherApi";


interface ModelType {
  username: string | null
  password: string | null
}

export default defineComponent({
  setup() {
    const formRef = ref<FormInst | null>(null)
    const rPasswordFormItemRef = ref<FormItemInst | null>(null)
    const message = useMessage()
    const use_store = useStore()
    const modelRef = ref<ModelType>({
      username: null,
      password: null
    })
    const rules: FormRules = {
      username: [
        {
          required: true,
          validator(rule: FormItemRule, value: string) {
            if (!value) {
              console.log(rule)
              return new Error('需要用户名')
            }
            return true
          },
          trigger: ['input', 'blur']
        }
      ],
      password: [
        {
          required: true,
          message: '请输入密码',
          validator(rule: FormItemRule, value: string) {
            if (!value) {
              console.log(rule)
              return new Error('需要账号')

            }
            return true
          },
          trigger: ['input', 'blur']
        }
      ]
    }
    const router = useRouter()
    const handleValidateButtonClick = (e: MouseEvent) => {
      e.preventDefault()
      // noinspection TypeScriptValidateTypes
      formRef.value?.validate((errors) => {
        if (!errors) {

          teacherApi(modelRef.value).then((res: any) => {
            if (res.code == 200) {
              use_store.user.username=res.data
              router.push("/back")
            }
          })
        } else {
          message.error('验证失败')
        }
      })
    }
    return {
      formRef,
      rPasswordFormItemRef,
      model: modelRef,
      rules,
      handleValidateButtonClick
    }
  }
})
</script>

<style scoped>
.main {
  width: 100%;
  height: 100vh;
  display: flex;
}

.left {
  width: 60%;
  height: 100%;
  background-image: url("/102.jpg");
  background-size: cover;
}

.right {
  width: 40%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.right h3 {
  margin-bottom: 20px;
}
</style>

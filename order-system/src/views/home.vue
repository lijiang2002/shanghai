<template>
  <main style="margin: 0 20px;">
    <n-card title="艺蕾音乐考试网登记" style="max-width: 860px;margin: 120px auto;" header-style="text-align:center">
      <n-hr/>
      <n-grid cols="1 550:2">
        <n-grid-item>
          <n-space justify="center">
            <img src="/101.png" width="300" alt="">
          </n-space>
        </n-grid-item>
        <n-grid-item>
          <n-space v-if="!showResult" vertical>
            <n-form ref="formRef" :model="formValue" :rules="rules">
              <n-form-item path="name" label="名字">
                <n-input v-model:value="formValue.name"/>
              </n-form-item>
              <n-form-item path="identity" label="身份证">
                <n-input
                    v-model:value="formValue.identity"
                />
              </n-form-item>
              <n-input-group>
                <n-form-item path="sy1" label="声乐1">
                  <n-input
                      v-model:value="formValue.sy1"
                      placeholder="请输入"
                  />
                </n-form-item>
                <n-form-item path="sy2" label="声乐2">
                  <n-input
                      v-model:value="formValue.sy2"
                      placeholder="请输入"
                  />
                </n-form-item>
                <n-form-item path="sy3" label="声乐3">
                  <n-input
                      v-model:value="formValue.sy3"
                      placeholder="请输入"
                  />
                </n-form-item>
              </n-input-group>
              <n-input-group>
                <n-form-item path="qy1" label="器乐1">
                  <n-input
                      v-model:value="formValue.qy1"
                      placeholder="请输入"
                  />
                </n-form-item>
                <n-form-item path="qy2" label="器乐2">
                  <n-input
                      v-model:value="formValue.qy2"
                      placeholder="请输入"
                  />
                </n-form-item>
                <n-form-item path="qy3" label="器乐3">
                  <n-input
                      v-model:value="formValue.qy3"
                      placeholder="请输入"
                  />
                </n-form-item>
              </n-input-group>
              <n-row :gutter="[0, 24]">
                <n-col :span="24">
                  <div style="display: flex; justify-content: flex-end">
                    <n-button
                        type="primary"
                        block
                        @click="handleValidateClick"
                    >
                      确认
                    </n-button>
                  </div>
                </n-col>
              </n-row>
            </n-form>
          </n-space>
          <n-space v-else align="center" justify="center">
            <n-result status="success" title="已成功提交" description="每个人都附有天赋">
              <n-space justify="center">
                <n-button><a href="/" style="text-decoration: none;color: #2fbe30">返回</a></n-button>
              </n-space>
            </n-result>

          </n-space>
        </n-grid-item>
      </n-grid>
    </n-card>
  </main>
</template>
<script lang="ts">
import {defineComponent, onUnmounted, ref} from 'vue'
import {FormInst, FormItemRule, FormRules, useMessage} from 'naive-ui'
import {examinationApi} from "@/api/examinationApi";
import axios from "axios";

export default defineComponent({
  setup() {

    interface ModelType {
      sy1: string | null
      sy2: string | null
      sy3: string | null
      sy4: string | null
      qy1: string | null
      qy2: string | null
      qy3: string | null
      qy4: string | null
      name: string | null
      identity: string | null
      cdate: number | null

    }

    const formRef = ref<FormInst | null>(null)
    const message = useMessage()
    const formValue = ref<ModelType>({
      sy1: null,
      sy2: null,
      sy3: null,
      sy4: null,
      qy1: null,
      qy2: null,
      qy3: null,
      qy4: null,
      name: null,
      identity: null,
      cdate: null
    })
    const rules: FormRules = {
      name: [
        {
          required: true,
          validator(rule: FormItemRule, value: string) {
            console.log(rule)
            if (!value) {
              return new Error('名字不能为空')
            }
            return true
          },
          trigger: ['input', 'blur']
        }
      ],
      identity: [
        {
          required: true,
          validator(rule: FormItemRule, value: string) {

            console.log(rule)
            if (!value) {
              return new Error('身份证不能为空')
            } else if (value.length != 18) {
              return new Error('身份证格式不正确')
            }
            return true
          },
          trigger: ['input', 'blur']
        }

      ],
      sy1: [
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
        }
      ],
      sy2:
          [
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
            }
          ], sy3:
          [
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
            }
          ], qy1:
          [
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
            }
          ], qy2:
          [
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
            }
          ], qy3:
          [
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
            }
          ]
    }
    const getRandomNumber = () => {
      // 生成一个0到2之间的随机数（不包括2）
      const random = Math.floor(Math.random() * 3);

      // 返回随机数 + 1，以获得1、2、3之间的随机整数
      return random + 1;
    }

    const handleValidateClick = (e: MouseEvent) => {
      e.preventDefault()
      // noinspection TypeScriptValidateTypes
      formRef.value?.validate((errors) => {
        if (!errors) {
          formValue.value.cdate = Date.now()
          // @ts-ignore
          formValue.value.sy4 = formValue.value[('sy' + getRandomNumber())]
          // @ts-ignore
          formValue.value.qy4 = formValue.value[('qy' + getRandomNumber())]
          examinationApi(formValue.value).then((res: any) => {
            if (res.code == 200) {
              showResult.value = true
            }
          })
        } else {
          console.log(errors)
          message.error('请正确填写并且完整')
        }
      })
    }
    const showResult = ref(false)
    const interval = setInterval(() => {
      axios.get(`http://127.0.0.1:19196/readcard&t=${Date.now()}`).then((res: any) => {
        if (res.data.certNumber.length > 8) {
          xinx.value = res;

          if (formValue.value.identity != res.data.certNumber) {
            xinx.value = res;
            formValue.value.identity = res.data.certNumber
            formValue.value.name = res.data.partyName
          }
        } else {
          xinx.value = res;
        }
      })
    }, 500);
    onUnmounted(() => {
      clearInterval(interval)
    })
    const xinx = ref<any>({
      "data": {
        "bornDay": "",
        "certAddress": "",
        "certNumber": "",
        "certOrg": "",
        "certType": "",
        "effDate": "",
        "errorMsg": "读卡失败",
        "expDate": "",
        "gender": "",
        "identityPic": "",
        "nation": "",
        "nationCode": "",
        "partyName": "",
        "resultFlag": -11
      },
      "status": 0,
      "statusText": "",
      "headers": {},
      "config": {
        "transitional": {"silentJSONParsing": true, "forcedJSONParsing": true, "clarifyTimeoutError": false},
        "adapter": ["xhr", "http"],
        "transformRequest": [null],
        "transformResponse": [null],
        "timeout": 0,
        "xsrfCookieName": "XSRF-TOKEN",
        "xsrfHeaderName": "X-XSRF-TOKEN",
        "maxContentLength": -1,
        "maxBodyLength": -1,
        "env": {},
        "headers": {"Accept": "application/json, text/plain, */*"},
        "method": "get",
        "url": ""
      },
      "request": "[object XMLHttpRequest]"
    })
    return {
      formRef,
      formValue,
      rules,
      showResult,
      handleValidateClick,
      xinx,
    }

  }
})
</script>

<style scoped>

</style>

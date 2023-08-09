<template>
  <n-alert title="温馨提示" type="warning">
    可直接刷用学生身份证，进入考试。
  </n-alert>
  <n-p/>
  <n-p/>
  <n-p/>
  <n-data-table
      :columns="columns"
      :data="data"
      :pagination="pagination"
      :bordered="false"
  />
  <n-modal
      :show="showModal"
      preset="card"
      title="艺蕾音乐模拟考试"
      :bordered="false"
      :mask-closable="false"
      style="height: 100vh;"
      @close="showModal=false"
  >
    <n-space vertical style="max-width: 1280px;margin: 0 auto">
      <n-row>
        <n-col :span="8">
          <n-statistic label="考生姓名">
            <template #suffix>
              {{ info.student.name }}
            </template>
          </n-statistic>
        </n-col>
        <n-col :span="16">
          <n-statistic label="身份证号" style="font-size: 20px !important;">
            {{ maskString(info.student.identity) }}
          </n-statistic>
        </n-col>
      </n-row>
      <n-space vertical v-if="!result">
        <n-divider title-placement="left">
          考试内容
        </n-divider>
        <n-scrollbar style="max-height: 70vh;padding: 0 20px" trigger="none">
          <n-space vertical>
            <n-collapse>
              <n-collapse-item title="声乐" name="1">
                <n-space vertical>
                  <n-statistic label="">
                    <n-space align="center" justify="space-between">
                      {{ info.sy4 }}
                      <n-space>
                        <n-text type="success">
                          <n-countdown :duration="220000" v-if="config.sy_a==2" :active="config.sy_a==2"/>
                        </n-text>
                        <n-button block v-if="config.sy_a==1" @click="config.sy_a=2">
                          开始考试
                        </n-button>
                        <n-button type="error" v-if="config.sy_a==2" @click="config.sy_a=3">
                          考试结束
                        </n-button>
                      </n-space>
                    </n-space>
                  </n-statistic>
                  <n-space>
                    <n-blockquote>
                      <n-text prefix="bar" style="font-size: 14px !important;color: #919191">预选（{{
                          info.sy1
                        }}、{{ info.sy2 }}、{{ info.sy3 }}）
                      </n-text>
                    </n-blockquote>
                  </n-space>
                  <n-image
                      :src="imgs.url1"
                  />
                </n-space>
                <n-form ref="sy_FormRef" :model="info" :rules="sy_rules">
                  <n-input-group v-if="config.sy_a==3">
                    <n-form-item path="s1" label="打分">
                      <n-input :allow-input="onlyAllowNumber" placeholder="分值1" v-model:value="info.s1"/>
                    </n-form-item>
                    <n-form-item path="s2">
                      <n-input :allow-input="onlyAllowNumber" placeholder="分值2" v-model:value="info.s2"/>
                    </n-form-item>
                    <n-form-item path="s3">
                      <n-input :allow-input="onlyAllowNumber" placeholder="分值3" v-model:value="info.s3"/>
                    </n-form-item>
                    <n-form-item path="s4">
                      <n-input :allow-input="onlyAllowNumber" placeholder="分值4" v-model:value="info.s4"/>
                    </n-form-item>
                    <n-form-item path="s5">
                      <n-input :allow-input="onlyAllowNumber" placeholder="分值5" v-model:value="info.s5"/>
                    </n-form-item>
                    <n-form-item>
                      <n-button type="primary" @click="sy_handleValidateClick">
                        确认
                      </n-button>
                    </n-form-item>
                  </n-input-group>
                  <n-space v-if="config.sy_a==4">
                    <n-tag type="success">{{ info.s1 }}</n-tag>
                    <n-tag type="success">{{ info.s2 }}</n-tag>
                    <n-tag type="success">{{ info.s3 }}</n-tag>
                    <n-tag type="success">{{ info.s4 }}</n-tag>
                    <n-tag type="success">{{ info.s5 }}</n-tag>
                  </n-space>
                </n-form>
              </n-collapse-item>
              <n-collapse-item title="器乐" name="2">
                <n-space vertical>
                  <n-statistic label="">
                    <n-space align="center" justify="space-between">
                      {{ info.qy4 }}
                      <n-space>
                        <n-text type="success">
                          <n-countdown :duration="340000" v-if="config.qy_a==2" :active="config.qy_a==2"/>
                        </n-text>
                        <n-button block v-if="config.qy_a==1" @click="config.qy_a=2">
                          开始考试
                        </n-button>
                        <n-button type="error" v-if="config.qy_a==2" @click="config.qy_a=3">
                          考试结束
                        </n-button>
                      </n-space>
                    </n-space>
                  </n-statistic>
                  <n-space>
                    <n-blockquote>
                      <n-text prefix="bar" style="font-size: 14px !important;color: #919191">预选（{{
                          info.qy1
                        }}、{{ info.qy2 }}、{{ info.qy3 }}）
                      </n-text>
                    </n-blockquote>
                  </n-space>
                  <n-image
                      :src="imgs.url2"
                  />
                </n-space>
                <n-form ref="qy_FormRef" :model="info" :rules="qy_rules">
                  <n-input-group v-if="config.qy_a==3">
                    <n-form-item path="q1" label="打分">
                      <n-input :allow-input="onlyAllowNumber" placeholder="分值1" v-model:value="info.q1"/>
                    </n-form-item>
                    <n-form-item path="q2">
                      <n-input :allow-input="onlyAllowNumber" placeholder="分值2" v-model:value="info.q2"/>
                    </n-form-item>
                    <n-form-item path="q3">
                      <n-input :allow-input="onlyAllowNumber" placeholder="分值3" v-model:value="info.q3"/>
                    </n-form-item>
                    <n-form-item path="q4">
                      <n-input :allow-input="onlyAllowNumber" placeholder="分值4" v-model:value="info.q4"/>
                    </n-form-item>
                    <n-form-item path="q5">
                      <n-input :allow-input="onlyAllowNumber" placeholder="分值5" v-model:value="info.q5"/>
                    </n-form-item>
                    <n-form-item>
                      <n-button type="primary" @click="qy_handleValidateClick">
                        确认
                      </n-button>
                    </n-form-item>
                  </n-input-group>
                  <n-space v-if="config.qy_a==4">
                    <n-tag type="success">{{ info.q1 }}</n-tag>
                    <n-tag type="success">{{ info.q2 }}</n-tag>
                    <n-tag type="success">{{ info.q3 }}</n-tag>
                    <n-tag type="success">{{ info.q4 }}</n-tag>
                    <n-tag type="success">{{ info.q5 }}</n-tag>
                  </n-space>
                </n-form>
              </n-collapse-item>
            </n-collapse>
            <n-button type="success" @click="save" block>
              考试结束
            </n-button>
          </n-space>
        </n-scrollbar>
      </n-space>
      <n-space justify="center" v-else style="margin-top: 100px">
        <n-result status="success" title="考试结束" description="请刷用身份证进入下一位同学进行考试">
        </n-result>
      </n-space>
    </n-space>

  </n-modal>
</template>

<script lang="ts">
import {h, defineComponent, ref, onMounted, watch, onUnmounted} from 'vue'
import {FormInst, FormItemRule, FormRules, NButton, useMessage} from 'naive-ui'
import type {DataTableColumns} from 'naive-ui'
import {getApi, infoApi, putApi} from "@/api/examinationApi";
import axios from "axios"
import {getListApi} from "@/api/fileApi";
import {url} from "@/main";

type Song = {
  name: number
  identity: string
}

export default defineComponent({
  setup() {
    onMounted(() => {
      getList()
      geImtList()

    })
    const fileList = ref<any>()
    const geImtList = () => {
      getListApi().then((res: any) => {
        for (let i = 0; i < res.data.length; i++) {
          res.data[i].url = url + "/img/" + res.data[i].name
          res.data[i].status = 'finished'
        }
        fileList.value = res.data
        console.log(fileList.value)
        getImgs()
      })
    }
    const imgs = ref({
      url1: 1,
      url2: 2
    })

    const getImgs=()=>{
      const random = Math.floor(Math.random() * fileList.value.length);
      imgs.value.url1=fileList.value[random].url
      const rando = Math.floor(Math.random() * fileList.value.length);
      imgs.value.url2=fileList.value[rando].url
    }

    const message = useMessage()
    const showModal = ref(false)
    const maskString = (input:any) => {
      if (typeof input !== "string" || input.length < 8) {
        return "Invalid input";
      }
      const maskedInput = input.substring(0, input.length - 4) + "****";
      return maskedInput;
    }
    const data = ref([])
    const config = ref({
      sy_a: 1,
      qy_a: 1,
    })
    const createColumns = (): DataTableColumns<Song> => {
      return [
        {
          title: '名字',
          key: 'name',
          render(row:any) {
            return row.student.name
          }
        },
        {
          title: '身份证号',
          key: 'identity',
          render(row:any) {
            return maskString(row.student.identity)
          }
        },
        {
          title: '操作',
          key: 'actions',
          render(row:any) {
            return h(
                NButton,
                {
                  strong: true,
                  tertiary: true,
                  type: 'success',
                  size: 'small',
                  onClick: () => {
                    xinx.value.data.certNumber = row.student.identity
                    showModal.value = true
                  }
                },
                {default: () => '考试'}
            )
          }
        }
      ]
    }
    const sy_rules: FormRules = {
      s1: [
        {
          required: true,
          validator(rule: FormItemRule, value: string) {
            console.log(rule)
            if (!value) {
              return new Error('请输入分值')
            }
            return true
          },
          trigger: ['input', 'blur']
        }
      ], s2: [
        {
          required: true,
          validator(rule: FormItemRule, value: string) {
            console.log(rule)
            if (!value) {
              return new Error('请输入分值')
            }
            return true
          },
          trigger: ['input', 'blur']
        }
      ], s3: [
        {
          required: true,
          validator(rule: FormItemRule, value: string) {
            console.log(rule)
            if (!value) {
              return new Error('请输入分值')
            }
            return true
          },
          trigger: ['input', 'blur']
        }
      ], s4: [{
        required: true,
        validator(rule: FormItemRule, value: string) {
          console.log(rule)
          if (!value) {
            return new Error('请输入分值')
          }
          return true
        },
        trigger: ['input', 'blur']
      }], s5: [{
        required: true,
        validator(rule: FormItemRule, value: string) {
          console.log(rule)
          if (!value) {
            return new Error('请输入分值')
          }
          return true
        },
        trigger: ['input', 'blur']
      }],
    }
    const qy_rules: FormRules = {
      q1: [
        {
          required: true,
          validator(rule: FormItemRule, value: string) {
            console.log(rule)
            if (!value) {
              return new Error('请输入分值')
            }
            return true
          },
          trigger: ['input', 'blur']
        }
      ], q2: [
        {
          required: true,
          validator(rule: FormItemRule, value: string) {
            console.log(rule)
            if (!value) {
              return new Error('请输入分值')
            }
            return true
          },
          trigger: ['input', 'blur']
        }
      ], q3: [
        {
          required: true,
          validator(rule: FormItemRule, value: string) {
            console.log(rule)
            if (!value) {
              return new Error('请输入分值')
            }
            return true
          },
          trigger: ['input', 'blur']
        }
      ], q4: [{
        required: true,
        validator(rule: FormItemRule, value: string) {
          console.log(rule)
          if (!value) {
            return new Error('请输入分值')
          }
          return true
        },
        trigger: ['input', 'blur']
      }], q5: [{
        required: true,
        validator(rule: FormItemRule, value: string) {
          if (!value) {
            console.log(rule)
            return new Error('请输入分值')
          }
          return true
        },
        trigger: ['input', 'blur']
      }],
    }
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
    const interval = setInterval(() => {
      axios.get(`http://127.0.0.1:19196/readcard&t=${Date.now()}`).then((res: any) => {
        if (res.data.certNumber.length > 8) {
          if (xinx.value.data.certNumber != res.data.certNumber) {
            xinx.value = res;
          }
        }
      })
    }, 500);
    onUnmounted(() => {
      clearInterval(interval)
    })
    const info = ref({
      "id": "3113175629",
      "sid": "0294671886",
      "date": 0,
      "cdate": 1691139595810,
      "status": "0",
      "sy1": "山行",
      "sy2": "万里",
      "sy3": "等级",
      "sy4": "等级",
      "qy1": "不待见",
      "qy2": "辜负",
      "qy3": "哇能",
      "qy4": "辜负",
      "s1": null,
      "s2": null,
      "s3": null,
      "s4": null,
      "s5": null,
      "q1": null,
      "q2": null,
      "q3": null,
      "q4": null,
      "q5": null,
      "student": {
        "id": "0294671886",
        "name": "王维",
        "identity": "4312151051215152",
        "status": "0",
        "examinations": null
      }
    });
    watch(xinx, () => {
      console.log(xinx.value.data.certNumber)
      // infoApi(xinx.value.data.certNumber)
      info.value = {
        "id": "",
        "sid": "",
        "date": 0,
        "cdate": 0,
        "status": "0",
        "sy1": "",
        "sy2": "",
        "sy3": "",
        "sy4": "",
        "qy1": "",
        "qy2": "",
        "qy3": "",
        "qy4": "",
        "s1": null,
        "s2": null,
        "s3": null,
        "s4": null,
        "s5": null,
        "q1": null,
        "q2": null,
        "q3": null,
        "q4": null,
        "q5": null,
        "student": {
          "id": "0",
          "name": "",
          "identity": "",
          "status": "",
          "examinations": null
        }
      }
      config.value = {
        sy_a: 1,
        qy_a: 1,
      }
      infoApi(xinx.value.data.certNumber).then((res: any) => {
        showModal.value = true
        info.value = res.data
      })
    }, {
      deep: true
    })
    const pagination = ref({
      page: 2,
      pageSize: 5,
      showSizePicker: true,
      pageSizes: [3, 5, 7],
      itemCount: 1,
      pageCount: 1,
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
    const getList = () => {
      getApi(pagination.value).then((res: any) => {
        data.value = res.data.records
        pagination.value.page = res.data.current as number
        pagination.value.itemCount = res.data.total as number
        pagination.value.pageCount = res.data.pages as number
      })
    }
    const sy_FormRef =  ref<FormInst | null>(null)
    const qy_FormRef =  ref<FormInst | null>(null)
    const sy_handleValidateClick = (e: MouseEvent) => {
      e.preventDefault()
      // noinspection TypeScriptValidateTypes
      sy_FormRef.value?.validate((errors:any) => {
        if (!errors) {
          config.value.sy_a = 4
        } else {
          console.log(errors)
          message.error('请把分数输入完整')
        }
      })
    }
    const qy_handleValidateClick = (e: MouseEvent) => {
      e.preventDefault()
      qy_FormRef.value?.validate((errors:any) => {
        if (!errors) {
          config.value.qy_a = 4
        } else {
          console.log(errors)
          message.error('请把分数输入完整')
        }
      })
    }
    const save = () => {
      if (config.value.sy_a == 4 && config.value.qy_a == 4) {
        info.value.status = "1"
        info.value.date = Date.now()
        putApi(info.value).then((res: any) => {
          if (res.code == 200) {
            result.value = true
          }
          getList()
        })
      } else {
        message.error("请完成全部考试")
      }
    }
    const result = ref(false)
    return {
      data,
      showModal,
      columns: createColumns(),
      maskString,
      pagination,
      info,
      config,
      sy_FormRef,
      qy_FormRef,
      qy_rules,
      sy_rules,
      onlyAllowNumber: (value: string) => !value || /^\d+$/.test(value),
      sy_handleValidateClick,
      qy_handleValidateClick,
      result,
      save,
      getList,
      imgs
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

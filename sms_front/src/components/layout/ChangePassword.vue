<template>
  <div class="ChangePassword">
    <el-dialog :before-close="closeDialog" :close-on-click-modal="false" :modal-append-to-body="false" :show-close="true"
               :title="title" :visible.sync="dialogShow" class="dia-changpass" top="22vh" width="660px">
      <m-form ref="mform" :columns="formColumns" :formData="formData"/>
      <div slot="footer" style="text-align:center;">
        <el-button style="margin-right:30px;padding: 10px 20px;" @click="closeDialog">取消</el-button>
        <el-button style="margin-bottom:20px;padding: 10px 20px;" type="primary" @click="updatePassWord">确认
        </el-button>
      </div>
    </el-dialog>
    <!-- 修改成功弹框 -->
    <ChangePassSuccInfo :dialogChangeShow='dialogChangeShow' class="edit-success" title=""
                        @closeChangeDialog="closeChangeDialog">
      <span style="font-size: 20px;color: #333333;font-weight: 400;">{{ changeSuccContent }}</span>
    </ChangePassSuccInfo>
  </div>
</template>

<script lang="tsx">
import {Component, Prop, Vue} from 'vue-property-decorator'
import ChangePassSuccInfo from './ChangePassSuccInfo.vue'

@Component({
  components: {
    ChangePassSuccInfo
  }
})
export default class ChangePassword extends Vue {
  @Prop([String]) title!: string
  @Prop([Boolean]) dialogShow!: boolean
  $refs!: {
    mform: any;
  }
  private formData = {
    oldPaw: '',
    newPaw: '',
    confirmNewPaw: ''
  } as any
  private changeSuccContent = '修改成功'
  private dialogChangeShow = false

  get formColumns() {
    return [
      {
        label: '旧密码：',
        prop: 'oldPaw',
        span: 22,
        el: 'input',
        type: 'password',
        'auto-complete': 'off',
        placeholder: '限制输入6-12位',
        rules: {
          required: true,
          trigger: 'blur',
          validator: (rule: any, value: string, callback: Function) => {
            if (value === '') {
              callback(new Error('请输入旧密码'))
            } else {
              if (this.formData.confirmNewPaw) {
                this.$refs.mform.validateField('confirmNewPaw')
              }
              callback()
            }
          }
        }
      },
      {
        label: '新密码：',
        prop: 'newPaw',
        span: 22,
        el: 'input',
        type: 'password',
        placeholder: '限制输入6-12位',
        rules: {
          required: true,
          trigger: 'blur',
          validator: (rule: any, value: string, callback: Function) => {
            if (value === '') {
              callback(new Error('请输入新密码'))
            } else {
              if (this.formData.confirmNewPaw !== '') {
                this.$refs.mform.validateField('confirmNewPaw')
              }
              callback()
            }
          }
        }
      },
      {
        label: '确认密码：',
        prop: '确认密码',
        span: 22,
        el: 'input',
        type: 'password',
        placeholder: '限制输入6-12位',
        rules: {
          required: true,
          trigger: 'blur',
          validator: (rule: any, value: string, callback: Function) => {
            if (value === '') {
              callback(new Error('请再次输入密码'))
            } else if (value !== this.formData.newPaw) {
              callback(new Error('两次输入密码不一致!'))
            } else if (value === this.formData.oldPaw) {
              callback(new Error('新密码与旧密码不能一致!'))
            } else {
              callback()
            }
          }
        }
      }
    ]
  }

  closeDialog() {
    this.resetForm()
    this.$emit('closeDialog')
    this.$store.commit('updateLogout', false)
  }

  closeChangeDialog() {
    this.dialogChangeShow = false
  }

  resetForm() {
    this.$refs.mform.resetFields()
  }

  updatePassWord() {
    this.$refs.mform.validate().then((value: boolean) => {
      if (value) {
        // TODO
        this.dialogChangeShow = true
        this.$emit('closeDialog')
        this.$store.commit('updateLogout', false)
        this.resetForm()
      }
    })
  }
}
</script>

<style lang="scss" scoped>
.ChangePassword {
  /deep/ .el-dialog {
    border-radius: 10px;
  }

  /deep/ .el-dialog__title {
    font-size: 16px;
    font-family: PingFangSC, PingFangSC-Semibold;
    font-weight: 600;
    text-align: left;
    color: #333333;
    line-height: 22px;
  }

  /deep/ .el-textarea__inner {
    height: 134px !important;
  }

  /deep/ .el-input__inner:focus {
    border-color: rgba(11, 178, 144, 1);
  }

  /deep/ .el-textarea__inner:focus {
    border-color: rgba(11, 178, 144, 1);
  }

  /deep/ .el-radio-group {
    width: 100%;
  }

  // 单选框部分
  /deep/ .el-radio__input.is-checked .el-radio__inner {
    border-color: rgba(11, 178, 144, 1);
    background: rgba(11, 178, 144, 1);
  }

  /deep/ .el-radio__input.is-checked + .el-radio__label {
    color: rgba(11, 178, 144, 1);
  }


  .dia-changpass {
    // 弹框按钮样式迭代
    // 朴素按钮样式迭代
    // hover
    /deep/ .el-button:first-child:hover {
      color: rgba(11, 178, 144, 1);
      border-color: rgba(11, 178, 144, 1);
    }

    /deep/ .el-button:first-child:focus {
      color: rgba(11, 178, 144, 1);
      border-color: rgba(11, 178, 144, 1);
    }
  }


}
</style>
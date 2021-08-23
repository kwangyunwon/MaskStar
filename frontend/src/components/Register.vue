<template>
  <v-dialog v-model="dialog" transition="fab-transition" max-width="1050">
    <template #activator="{ on, attrs }">
      <v-list-item-title v-bind="attrs" v-on="on">회원가입</v-list-item-title>
    </template>
    <v-card>
      <v-card-title>
        <span class="text-h5">회원가입</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-form ref="form" v-model="valid" lazy-validation>
            <v-row>
              <v-col cols="9">
                <v-text-field
                  v-model="user_id"
                  label="아이디*"
                  :rules="user_id_rule"
                  required
                >
                </v-text-field>
              </v-col>
              <v-col cols="3">
                <v-checkbox
                  label="중복체크"
                  v-model="check"
                  :rules="check_rule"
                  required
                  @click="idCheck"
                >
                </v-checkbox>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  v-model="user_password"
                  label="비밀번호*"
                  type="password"
                  :rules="user_password_rule"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  v-model="user_password_check"
                  label="비밀번호 확인*"
                  type="password"
                  :rules="user_password_check_rule"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  v-model="user_name"
                  label="이름*"
                  :rules="user_name_rule"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="9">
                <v-text-field
                  v-model="user_nickname"
                  label="닉네임*"
                  :rules="user_nickname_rule"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="3">
                <v-checkbox
                  label="중복체크"
                  v-model="n_check"
                  :rules="n_check_rule"
                  required
                  @click="nicknameCheck"
                >
                </v-checkbox>
              </v-col>
              <v-col cols="12" sm="6">
                <v-text-field
                  v-model="user_email"
                  label="이메일*"
                  :rules="user_email_rule"
                  required
                  ><template v-slot:append>
                    <v-btn color="primary" small @click="sendEmail"
                      >인증번호 전송
                    </v-btn>
                  </template>
                </v-text-field>
              </v-col>
              <v-col cols="12" sm="6">
                <v-text-field
                  v-model="user_email_check"
                  label="인증번호*"
                  :rules="user_email_check_rule"
                  required
                ></v-text-field>
              </v-col>

              <v-col cols="12" sm="12">
                <v-menu
                  ref="menu"
                  v-model="menu"
                  :close-on-content-click="false"
                  :return-value.sync="date"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field
                      v-model="date"
                      label="생년월일"
                      v-bind="attrs"
                      readonly
                      v-on="on"
                      :rules="date_rule"
                    ></v-text-field>
                  </template>
                  <v-date-picker v-model="date" no-title scrollable>
                    <v-spacer></v-spacer>
                    <v-btn text color="primary" @click="$refs.menu.save(date)">
                      OK
                    </v-btn>
                    <v-btn text color="primary" @click="menu = false">
                      Cancel
                    </v-btn>
                  </v-date-picker>
                </v-menu>
              </v-col>
            </v-row>
            <small>* 필수로 입력</small>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn
                :disabled="!valid"
                color="blue darken-1"
                text
                @click="submit"
              >
                회원가입
              </v-btn>
              <v-btn color="blue darken-1" text @click="close">
                취소
              </v-btn>
            </v-card-actions>
          </v-form>
        </v-container>
      </v-card-text>
    </v-card>
  </v-dialog>
</template>
<script>
import axios from 'axios'
// eslint-disable-next-line no-unused-vars
var key

export default {
  data () {
    return {
      // v-model 초기화
      dialog: false,
      date: '',
      menu: false,
      valid: true,
      user_id: '',
      user_password: '',
      user_password_check: '',
      user_name: '',
      check: false,
      user_nickname: '',
      user_email: '',
      user_email_check: '',
      n_check: false,
      
      // Rule
      user_id_rule: [
        v => !!v || '아이디는 필수 입력사항입니다.',
        v =>
          /^[a-zA-Z0-9]*$/.test(v) || '아이디는 영문+숫자만 입력 가능합니다.',
        v => !(v && v.length >= 15) || '아이디는 15자 이상 입력할 수 없습니다.',
        v => !(v && v.length < 5) || '아이디는 4자 이하 입력할 수 없습니다.'
      ],

      check_rule: [v => !!v || '중복체크를 안하셨습니다'],

      user_password_rule: [
        v => !!v || '패스워드는 필수 입력 사항입니다.',
        v =>
          !(v && v.length >= 15) || '패스워드는 15자 이상 입력할 수 없습니다.',
        v => !(v && v.length <= 6) || '패스워드는 6자 이하 입력할 수 없습니다.'
      ],

      user_password_check_rule: [
        v => !!v || '패스워드는 필수 입력 사항입니다.',
        v => !(v && v.length >= 15) || '패스워드 15자 이상 입력할 수 없습니다.',
        v => v === this.user_password || '패스워드가 일치하지 않습니다.',
        v => !(v && v.length <= 6) || '패스워드는 6자 이하 입력할 수 없습니다.'
      ],

      user_name_rule: [
        v => !!v || '이름은 필수 입력사항입니다.',
        v => !(v && v.length >= 30) || '이름은 15자 이상 입력할 수 없습니다.',
        v =>
          !/[~!@#$%^&*()_+|<>?:{}]/.test(v) ||
          '이름에 특수문자를 사용 할 수 없습니다.'
      ],

      user_nickname_rule: [
        v => !!v || '닉네임은 필수 입력사항입니다.',
        v => !(v && v.length >= 15) || '닉네임은 15자 이상 입력할 수 없습니다.',
        v =>
          !/[~!@#$%^&*()_+|<>?:{}]/.test(v) ||
          '닉네임에 특수문자를 사용 할 수 없습니다.',
        v => !(v && v.length <= 2) || '닉네임 2자 이하 입력할 수 없습니다.'
      ],

      user_email_rule: [v => !!v || '이메일은 필수 입력사항입니다.'],

      user_email_check_rule: [
        v => !!v || '인증번호를 입력하세요',
        v => v == key || '인증번호가 일치하지 않습니다'
      ],
      date_rule: [v => !!v || '생년월일을 입력하세요'],

      n_check_rule: [v => !!v || '중복체크를 안하셨습니다']
    }
  },
  created() {
      this.$emit('create_routerview', 'Register');
  },
  methods: {
    submit () {
      const validDate = this.$refs.form.validate()
      if (confirm('회원 가입 하겠습니까?')) {
        const registerForm = {
          id: this.user_id,
          name: this.user_name,
          password: this.user_password,
          email: this.user_email,
          nickname: this.user_nickname,
          birthdate: this.date
        }
        console.log(validDate)
        if (validDate == true) {
          axios
            .post('http://api.maskstar.kr/signup', registerForm)
            .then(response => {
              console.log(response)
              alert(response['data'])
              this.$refs.form.reset()
            })
            .catch(ex => {
              console.log(ex)
            })
        }
      }
    },
    close () {
      this.dialog = false
      this.$refs.form.reset()
      this.$refs.form.resetValidation()
    },
    sendEmail () {
      axios
        .get('http://api.maskstar.kr/mypage/emailcheck/' + this.user_email)
        .then(response => {
          console.log(response)
          if (!response['data']) {
            const url = this.user_email.split('@')
            axios
              .post('http://api.maskstar.kr/signup/email', url)
              .then(response => {
                key = response['data']
                console.log(key)
                alert('인증번호가 전송되었습니다.')
              })
              .catch(ex => {
                console.log(ex)
              })
          } else {
            alert('이미 사용한 이메일입니다.')
          }
        })
        .catch(ex => {
          console.log(ex)
        })
    },
    idCheck () {
      var temp
      if (this.user_id != null) {
        axios
          .get('http://api.maskstar.kr/mypage/idcheck/' + this.user_id)
          .then(response => {
            console.log(response)
            if (!response['data']) {
              temp = true
            } else {
              temp = false
              this.check = false
            }
            this.check_rule = [
              temp || '중복입니다',
              v => !!v || '중복체크를 안하셨습니다'
            ]
          })
          .catch(ex => {
            console.log(ex)
          })
      }
    },
    nicknameCheck () {
      var temp
      if (this.user_nickname != null) {
        axios
          .get('http://api.maskstar.kr/mypage/nicknamecheck/' + this.user_nickname)
          .then(response => {
            console.log(response)
            if (!response['data']) {
              temp = true
            } else {
              temp = false
              this.n_check = false
            }
            this.n_check_rule = [
              temp || '중복입니다',
              v => !!v || '중복체크를 안하셨습니다'
            ]
          })
          .catch(ex => {
            console.log(ex)
          })
      }
    }
  }
}
</script>

<style lang="scss"></style>

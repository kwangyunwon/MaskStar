<template>
  <div id="channel-home">
    <div class="nav-bgcolor">
      <div id="channel-header">
      </div>
      <v-card flat class="transparent">
        <v-tabs
          v-model="tab"
          background-color="transparent"
          show-arrows
          centered
          center-active
        >
          <v-tab v-for="(item, i) in items" :key="i">
            {{ item }}
          </v-tab>
        </v-tabs>

        <v-container fluid>
          <v-tabs-items v-model="tab" class="transparent">
            <v-tab-item>
              <v-card class="transparent" flat>
                <v-card-title>내가 쓴 글</v-card-title>
                <v-row>
                  <v-col
                    cols="12"
                    sm="6"
                    md="4"
                    lg="3"
                    v-for="i in loading ? 10 : boards.length"
                    :key="i"
                    class="mx-xs-auto"
                  >
                    <v-skeleton-loader type="card-avatar" :loading="loading">
                      <video-card
                        :card="{ maxWidth: 350 }"
                        :board="boards[i - 1]"
                      ></video-card>
                    </v-skeleton-loader>
                  </v-col>
                </v-row>
              </v-card>
              <infinite-loading
                @infinite="infiniteHandler"
                spinner="waveDots"
              >
              <span slot="no-more"></span>
              <span slot="no-results"></span>
              </infinite-loading>
            </v-tab-item>
            <v-tab-item>
              <v-form ref="form" v-model="valid" lazy-validation>
                <v-card
                  :loading="loading"
                  class="mx-auto my-12"
                  max-width="600"
                >
                  <v-card-title>개인정보 수정</v-card-title>

                  <v-card-text>
                    <v-row align="center" class="mx-0">
                      <v-col cols="12" sm="12">
                        <v-text-field
                          v-model="id_field"
                          readonly
                          label="아이디*"
                          required
                        >
                        </v-text-field>
                      </v-col>
                      <v-col cols="12" sm="12">
                        <v-text-field
                          v-model="name_field"
                          label="이름*"
                          required
                        >
                        </v-text-field>
                      </v-col>
                      <v-col cols="9">
                        <v-text-field
                          v-model="update_nickname"
                          label="닉네임*"
                          :rules="update_nickname_rule"
                          required
                        ></v-text-field>
                      </v-col>
                      <v-col cols="3">
                        <v-checkbox
                          label="중복체크"
                          v-model="update_nickname_check"
                          :rules="update_nickname_check_rule"
                          required
                          @click="nicknameCheck"
                        >
                        </v-checkbox>
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
                            <v-btn
                              text
                              color="primary"
                              @click="$refs.menu.save(date)"
                            >
                              OK
                            </v-btn>
                            <v-btn text color="primary" @click="menu = false">
                              Cancel
                            </v-btn>
                          </v-date-picker>
                        </v-menu>
                      </v-col>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="deep-purple lighten-2"
                          text
                          @click="submit"
                        >
                          변경
                        </v-btn>
                      </v-card-actions>
                    </v-row>
                  </v-card-text>
                </v-card>
              </v-form>
            </v-tab-item>
            <v-tab-item>
              <v-form ref="form" v-model="p_valid" lazy-validation>
                <v-card
                  :loading="loading"
                  class="mx-auto my-12"
                  max-width="600"
                >
                  <v-card-title>비밀번호 변경</v-card-title>
                  <v-card-text>
                    <v-row align="center" class="mx-0">
                      <v-col cols="12" sm="12">
                        <v-text-field
                          v-model="pwd_field"
                          :rules="pwd_field_rule"
                          label="비밀번호*"
                          type="password"
                          required
                        >
                        </v-text-field>
                      </v-col>
                      <v-col cols="12" sm="12">
                        <v-text-field
                          v-model="pwd_field_check"
                          :rules="pwd_field_check_rule"
                          label="비밀번호 확인*"
                          type="password"
                          required
                        >
                        </v-text-field>
                      </v-col>

                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn
                          color="deep-purple lighten-2"
                          text
                          @click="submit_pwd"
                        >
                          변경
                        </v-btn>
                      </v-card-actions>
                    </v-row>
                  </v-card-text>
                </v-card>
              </v-form>
            </v-tab-item>
            <v-tab-item>
              <v-form ref="form" v-model="p_valid" lazy-validation>
                <v-card
                  :loading="loading"
                  class="mx-auto my-12"
                  max-width="600"
                >
                  <v-card-title>회원탈퇴</v-card-title>
                  <v-card-text>
                    <v-row align="center" class="mx-0">
                      <v-card-actions>
                        <v-btn
                          color="deep-purple lighten-2"
                          text
                          @click="deleteUser"
                        >
                          탈퇴
                        </v-btn>
                      </v-card-actions>
                    </v-row>
                  </v-card-text>
                </v-card>
              </v-form>
            </v-tab-item>
          </v-tabs-items>
        </v-container>
      </v-card>
    </div>
  </div>
</template>

<script>
import VideoCard from '@/components/VideoCard'
import axios from 'axios'

import InfiniteLoading from 'vue-infinite-loading'
export default {
  data () {
    return {
      valid: true,
      p_valid: true,
      user: {},
      id_field: '',
      name_field: '',
      date: '',
      menu: false,
      tab: 0,
      loading: true,
      board: {},
      boards: [],
      start: 0,
      update_nickname: '',
      update_nickname_rule: [
        v => !!v || '닉네임은 필수 입력사항입니다.',
        v => !(v && v.length >= 15) || '닉네임은 15자 이상 입력할 수 없습니다.',
        v =>
          !/[~!@#$%^&*()_+|<>?:{}]/.test(v) ||
          '닉네임에 특수문자를 사용 할 수 없습니다.',
        v => !(v && v.length <= 2) || '닉네임 2자 이하 입력할 수 없습니다.'
      ],
      name_field_rule: [
        v => !!v || '이름은 필수 입력사항입니다.',
        v => !(v && v.length >= 30) || '이름은 15자 이상 입력할 수 없습니다.',
        v =>
          !/[~!@#$%^&*()_+|<>?:{}]/.test(v) ||
          '이름에 특수문자를 사용 할 수 없습니다.'
      ],
      date_rule: [v => !!v || '생년월일을 입력하세요'],

      pwd_field: '',
      pwd_field_rule: [
        v => !!v || '패스워드는 필수 입력 사항입니다.',
        v =>
          !(v && v.length >= 15) || '패스워드는 15자 이상 입력할 수 없습니다.',
        v => !(v && v.length <= 6) || '패스워드는 6자 이하 입력할 수 없습니다.'
      ],
      pwd_field_check: '',
      pwd_field_check_rule: [
        v => !!v || '패스워드는 필수 입력 사항입니다.',
        v => !(v && v.length >= 15) || '패스워드 15자 이상 입력할 수 없습니다.',
        v => v === this.pwd_field || '패스워드가 일치하지 않습니다.',
        v => !(v && v.length <= 6) || '패스워드는 6자 이하 입력할 수 없습니다.'
      ],

      update_nickname_check: '',
      update_nickname_check_rule: [],

      items: ['내가 쓴 글', '개인정보 수정', '비밀번호 변경', '회원탈퇴']
    }
  },
  components: {
    InfiniteLoading,
    VideoCard
  },
  methods: {
    loadMyvideo () {
      //처음 로드될 영상들, 이후 무한스크롤핸들러가 처리한다
      axios
        .get('http://api.maskstar.kr/board/searchnickname', {
          params: {
            sort: 'MY',
            keyword: localStorage.getItem('nickname'), //TODO : 닉네임 연결
            start: this.start
          }
        })
        .then(response => (this.boards = response.data))
        .catch(error => {
          console.log(error)
          this.errored = true
        })
        .finally(() => (this.loading = false))
      this.start += 5
    },
    infiniteHandler ($state) {
      axios
        .get('http://api.maskstar.kr/board/searchnickname', {
          params: {
            sort: 'MY',
            keyword: localStorage.getItem('nickname'), //TODO : 닉네임 연결
            start: this.start
          }
        })
        .then(response => {
          setTimeout(() => {
            if (response.data.length) {
              this.boards = this.boards.concat(response.data)
              $state.loaded()
              this.start += 5
              if (this.boards.length / 5 == 0) {
                $state.complete()
              }
            } else {
              $state.complete()
            }
          }, 1000)
        })
        .catch(error => {
          console.log(error)
        })
    },
    nicknameCheck () {
      var temp
      if (this.update_nickname != null) {
        axios
          .get(
            'http://api.maskstar.kr/mypage/nicknamecheck/' + this.update_nickname
          )
          .then(response => {
            console.log(response)
            if (!response['data']) {
              temp = true
            } else {
              temp = false
              this.update_nickname_check = false
            }
            this.update_nickname_check_rule = [
              temp || '중복입니다',
              v => !!v || '중복체크를 안하셨습니다'
            ]
          })
          .catch(ex => {
            console.log(ex)
          })
      }
    },
    submit () {
      if (confirm('수정 하겠습니까?')) {
        const validDate = this.$refs.form.validate()
        const updateForm = {
          id: this.id_field,
          name: this.name_field,
          nickname: this.update_nickname,
          birthdate: this.date
        }
        if (validDate == true) {
          axios
            .put('http://api.maskstar.kr/mypage/update', updateForm)
            .then(response => {
              console.log(response)
              alert(response['data'])
              location.reload()
            })
            .catch(ex => {
              console.log(ex)
              alert('수정 실패 !')
            })
        }
      }
    },
    submit_pwd () {
      if (confirm('수정 하겠습니까?')) {
        const validDate = this.$refs.form.validate()
        const passwordForm = {
          id: localStorage.getItem('login_id'),
          password: this.pwd_field
        }
        if (validDate == true) {
          axios
            .put('http://api.maskstar.kr/mypage/update/password', passwordForm)
            .then(response => {
              console.log(response)
              alert(response['data'])
              location.reload()
            })
            .catch(ex => {
              console.log(ex)
              alert('수정 실패 !')
            })
        }
      }
    },
    deleteUser () {
      if (confirm('탈퇴 하시겠습니까?')) {
        axios
          .delete(
            'http://api.maskstar.kr/mypage/delete/' +
              localStorage.getItem('login_id')
          )
          .then(response => {
            console.log(response)
            alert(response['data'])
            location.reload()
          })
          .catch(ex => {
            console.log(ex)
          })
      }
    }
  },

  created () {
    this.$emit('create_routerview', 'mypage')
  },
  mounted () {
    if(localStorage.getItem('token')!=null){
      axios.defaults.headers.common['Authorization']='Bearer '+localStorage.getItem('token')
    }
    axios
      .get(
        'http://api.maskstar.kr/mypage/user/' + localStorage.getItem('id'),
      )
      .then(response => {
        console.log(this.user.id)
        this.user = response['data']
        this.id_field = this.user.id
        this.name_field = this.user.name
        this.update_nickname = this.user.nickname
        this.date = this.user.birthdate
      })
      .catch(ex => {
        console.log(ex)
      }),
      setTimeout(() => {
        this.loading = false
      }, 3000)
    this.loadMyvideo()
  }
}
</script>

<style>
.v-tab {
  margin-right: 4em;
}

#channel-home .v-list-item--link:before {
  background-color: transparent;
}
</style>

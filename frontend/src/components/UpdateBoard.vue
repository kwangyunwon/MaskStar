<template>
  <v-dialog v-model="dialog" transition="fab-transition" max-width="1050">
    <template #activator="{ on, attrs }">
      <v-list-item-title v-bind="attrs" v-on="on" style="font-size:13px"
        >수정</v-list-item-title
      >
    </template>
    <v-card>
      <v-card-title>
        <span class="text-h5">수정</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-form ref="form" v-model="valid" lazy-validation>
            <v-row>
              <v-col cols="4">
                <v-text-field
                  v-model="title"
                  label="노래*"
                  :rules="title_rule"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="4">
                <v-text-field
                  v-model="singer"
                  label="가수*"
                  :rules="singer_rule"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="4">
                <v-select
                  label="장르*"
                  v-model="genre"
                  :items="option"
                  item-text="name"
                  item-value="value"
                  required
                ></v-select>
              </v-col>
              <v-textarea
                solo
                v-model="content"
                label="내용"
                :rules="desc_rule"
                required
              ></v-textarea>
            </v-row>
            <small>* 필수로 입력</small>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="updateBoard">
                수정
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

export default {
  data () {
    return {
      valid: true,
      dialog: false,
      title_rule: [v => !!v || '제목은 필수 입력사항입니다.'],
      singer_rule: [v => !!v || '가수는 필수 입력사항입니다.'],
      desc_rule: [v => !!v || '설명은 필수 입력사항입니다.'],
      option: [
        { name: '발라드', value: 0 },
        { name: '힙합', value: 1 },
        { name: '댄스', value: 2 },
        { name: '기타', value: 3 }
      ],
      title: '',
      singer: '',
      content: '',
      genre: '',
      form: {}
    }
  },
  props: {
    board: {
      type: Object,
      required: true
    }
  },
  created () {
    this.$emit('create_routerview', 'UpdateBoard')
  },

  methods: {
    updateBoard () {
      if (confirm('글을 수정하시겠습니까?')) {
        this.form = {
          boardTitle: this.title,
          boardSinger: this.singer,
          boardGenre: this.genre,
          boardContent: this.content,
          boardIdx: this.boardIdx
        }
        axios
          .put('http://api.maskstar.kr/board/updateboard', this.form)
          .then(response => {
            if (response.status != 200) {
              alert('글 수정에 실패하였습니다')
            } else {
              alert('글이 수정되었습니다.')
            }

            this.dialog = false
            location.reload()
          })
          .catch(error => {
            console.log(error)
            this.errored = true
          })
      }
    },
    close () {
      this.dialog = false
      this.$refs.form.reset()
      this.$refs.form.resetValidation()
    }
  },
  mounted () {
      (this.title = this.board.boardTitle),
      (this.singer = this.board.boardSinger),
      (this.genre = this.board.boardGenre),
      (this.content = this.board.boardContent),
      (this.boardIdx = this.board.boardIdx)
  }
}
</script>

<style lang="scss"></style>

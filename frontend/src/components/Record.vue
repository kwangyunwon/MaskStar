<template>
  <div>
    <div class="top">
      <div class="videoImg">
        <img :src="url" width="100%" height="500px" />
      </div>
      <div class="youtube">
        <youtube
          :video-id="videoId"
          ref="youtube"
          @playing="playing"
          width="100%"
          height="500px"
          v-if="youtubeLoading"
        ></youtube>
        <img v-bind:src="selectedImage" width="100%" height="500px" v-else />
      </div>
    </div>

    <div>
      <div class="btn">
        <v-btn
          elevation="2"
          color="success"
          large
          @click="recordStart()"
          v-if="record == 1"
        >
          녹화시작</v-btn
        >&nbsp;&nbsp;
        <v-btn
          elevation="2"
          color="blue"
          large
          @click="playVideo"
          v-if="record == 1"
        >
          노래시작</v-btn
        >
        <v-btn
          elevation="2"
          color="error"
          large
          @click="recordStop()"
          v-if="record == 2"
        >
          녹화 완료</v-btn
        >&nbsp;&nbsp;

        <v-btn
          elevation="2"
          color="warning"
          large
          @click="resetRecord()"
          v-if="record == 2"
        >
          녹화 취소</v-btn
        >
  
        <v-btn elevation="2" color="deep-orange" dark large v-if="record == 3">
          <Upload :boardVideo="boardVideo" /> 
        </v-btn>
        &nbsp;&nbsp;
        <v-btn
          elevation="2"
          color="warning"
          large
          @click="deleteVideo()"
          v-if="record == 3"
        >
          다시 시작</v-btn
        >
      </div>
      <div class="youtubeSearch">
        <v-col cols="12" sm="6" md="8" class="searchbtn">
          <v-text-field
            solo
            dark
            label="YOUTUBE 검색"
            clearable
            append-icon="mdi-magnify"
            v-model="searchYoutubeText"
            @click:append="searchYoutube"
            @keyup.enter="searchYoutube"
          ></v-text-field>
        </v-col>
      </div>
    </div>

    <div>
      <div class="sticker">
        <v-slide-group
          v-model="slides"
          class="pa-4"
          active-class="success"
          show-arrows
        >
          <v-slide-item v-for="(slide, i) in slides" :key="i">
            <v-card class="ma-4" width="100" @click="imgUpdate(slide.title)">
              <v-img height="100px" contain :src="slide.image"></v-img>
            </v-card>
          </v-slide-item>
        </v-slide-group>
      </div>
       <div class="youtubeRe">
        <v-slide-group
          v-model="yotubeResult"
          active-class="success"
          show-arrows
        >
          <v-slide-item v-for="(re, i) in yotubeResult" :key="i">
            <v-card
              :elevation="re ? 16 : 2"
              width="270"
              @click="imgClick(re.id)"
              class="ma-4"
            >
              <v-img :src="re.url" height="150px"></v-img>
              <v-card-title height="100">{{ re.title }}</v-card-title>
            </v-card>
          </v-slide-item>
        </v-slide-group>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import Vue from 'vue'
import VueYoutube from 'vue-youtube'
import Upload from './Upload.vue'
import cute from '@/assets/AI/cute.png'
import dog from '@/assets/AI/dog.png'
import emoji from '@/assets/AI/emoji.png'
import emoji2 from '@/assets/AI/emoji2.png'
import emoji3 from '@/assets/AI/emoji3.png'
import fox from '@/assets/AI/fox.png'
import ghost from '@/assets/AI/ghost.png'
import heart from '@/assets/AI/heart.png'
import pngegg from '@/assets/AI/pngegg.png'
import ryan_transparent from '@/assets/AI/ryan_transparent.png'
import sad from '@/assets/AI/sad.png'
import smile from '@/assets/AI/smile.png'

import youtube1 from '@/assets/youtube/001.png'
import youtube2 from '@/assets/youtube/002.png'
import youtube3 from '@/assets/youtube/003.png'
import youtube4 from '@/assets/youtube/004.png'
import youtube5 from '@/assets/youtube/005.png'
import youtube6 from '@/assets/youtube/006.png'

Vue.use(VueYoutube)

export default {
  name: 'Home',
  data () {
    return {
      url: '',
      videoId: '',
      record: 1,
      mediaRecorder: null,
      chunks: [],
      boardVideo: '',
      loading: true,
      slides: [
        {
          title: 'cute',
          image: cute
        },
        {
          title: 'dog',
          image: dog
        },
        {
          title: 'emoji',
          image: emoji
        },
        {
          title: 'emoji2',
          image: emoji2
        },
        {
          title: 'emoji3',
          image: emoji3
        },
        {
          title: 'fox',
          image: fox
        },
        {
          title: 'ghost',
          image: ghost
        },
        {
          title: 'heart',
          image: heart
        },
        {
          title: 'pngegg',
          image: pngegg
        },
        {
          title: 'ryan_transparent',
          image: ryan_transparent
        },
        {
          title: 'sad',
          image: sad
        },
        {
          title: 'smile',
          image: smile
        }
      ],
      searchYoutubeText: '',
      yotubeResult: [],
      youtubeLoading: false,
      videoLoading: false,
      images: [
        youtube1,
        youtube2,
        youtube3,
        youtube4,
        youtube5,
        youtube6,
      ],
      selectedImage: ''
    }
  },
  created () {
    this.$emit('create_routerview', 'record');
    const idx = Math.floor(Math.random() * this.images.length)
    this.selectedImage = this.images[idx]
  },
  components: {
    Upload
  },
  destroyed () {
    axios({
      method: 'GET',
      url: 'http:/127.0.0.1:8000/endRecord'
    })
      .then(response => {
        console.log('sucess', response)
      })
      .catch(response => {
        console.log('Failed', response)
      })
  },

  mounted () {
    var now = new Date()
    this.url = 'http:/127.0.0.1:8000/sticker?' + now.getTime()
    this.resetVideo()
    this.naming()
  },

  computed: {
    player () {
      return this.$refs.youtube.player
    }
  },
  methods: {
    playVideo () {
      this.player.playVideo()
    },
    playing () {
      console.log(' we are watching!!!')
    },
    resetVideo () {
      const constraints = {
        audio: true,
        video: false
      }
      navigator.mediaDevices
        .getUserMedia(constraints)
        .then(this.gotStream)
        .catch(err => {
          console.log('The following error occurred : ' + err)
        })
    }, //resetVideo
    recordStart () {
      console.log('record Start')
      this.record = 2
      let filedata = new FormData()
      filedata.append(
        'filename', this.boardVideo
      )

      axios({
        method: 'POST',
        url: 'http:/127.0.0.1:8000/record_video',
        data: filedata
      })
        .then(response => {
          console.log('sucess', response)
        })
        .catch(response => {
          console.log('Failed', response)
        })
      this.mediaRecorder.start()
    },

    recordStop () {
      axios({
        method: 'GET',
        url: 'http:/127.0.0.1:8000/record_stop'
      })
        .then(response => {
          console.log('sucess', response)
          this.loading = false
        })
        .catch(response => {
          console.log('Failed', response)
          this.loading = false
        })
      this.mediaRecorder.stop()

      setTimeout(() => {
        this.finalSave()
        this.record = 3
      }, this.loading)
      this.loading = true
    }, //record_stop

    resetRecord () {
      let filedata = new FormData()
      filedata.append(
        'filename', this.boardVideo
      )
      filedata.append('flag', '2')
      axios({
        method: 'POST',
        url: 'http:/127.0.0.1:8000/record_delete',
        data: filedata
      })
        .then(response => {
          console.log('sucess', response)
        })
        .catch(response => {
          console.log('Failed', response)
        })
      this.mediaRecorder.stop()
      this.chunks = []
      this.record = 1
    },

    gotStream (mediaStream) {
      console.log('getStream')
      this.mediaRecorder = new MediaRecorder(mediaStream, {
        type: 'audio/wav'
      })
      this.mediaRecorder.ondataavailable = this.videoDataHandler
    }, //gotStream

    naming () {
      var today = new Date()
      var year = today.getFullYear()
      var month = ('0' + (today.getMonth() + 1)).slice(-2)
      var day = ('0' + today.getDate()).slice(-2)
      var hours = ('0' + today.getHours()).slice(-2)
      var minutes = ('0' + today.getMinutes()).slice(-2)
      var seconds = ('0' + today.getSeconds()).slice(-2)

      this.boardVideo =
        localStorage.getItem('id') +
        year +
        month +
        day +
        hours +
        minutes +
        seconds
    },
    finalSave () {
      let blob = new Blob(this.chunks, {
        type: 'audio/wav; codecs=0'
      })

      this.chunks = []
      let formdata = new FormData()

      formdata.append('filename', this.boardVideo)
      formdata.append('data', blob)

      axios({
        method: 'post',
        url: 'http:/127.0.0.1:8000/audio_save',
        headers: {
          'Content-Type': 'multipart/form-data'
        },
        data: formdata
      })
        .then(response => {
          this.boardVideo = this.boardVideo + '_out.mp4'
          console.log('sucess', response)
        })
        .catch(response => {
          console.log('audio_save error', response)
        })
    },

    videoDataHandler (event) {
      this.chunks.push(event.data)
    },
    imgUpdate (fname) {
      axios({
        method: 'GET',
        url: 'http:/127.0.0.1:8000/img_update?filename=' + fname
      })
        .then(response => {
          console.log('sucess', response)
        })
        .catch(response => {
          console.log('audio_save error', response)
        })
    },

    searchYoutube () {
      axios.defaults.headers.common['Authorization']=''
      this.yotubeResult = []
      console.log(this.searchYoutubeText)
      if (!this.searchYoutubeText) return
      var url = 'https://www.googleapis.com/youtube/v3/search'
      var params = {
        q: this.searchYoutubeText,
        key: 'AIzaSyAPUC_pSwLHLPTZK4NZbRiV2bQsbXsFWnE',
        part: 'snippet',
        type: 'video',
        maxResults: 8,
        regionCode: 'KR'
      }

      axios
        .get(url, {
          params: params
        })
        .then(response => {
          var items = response.data.items
          console.log(items)
          for (var i in items) {
            var result = {
              title: items[i].snippet.title.replace(/[&#39;]+/g, "'"),
              id: items[i].id.videoId,
              url: items[i].snippet.thumbnails.medium.url
            }
            this.yotubeResult.push(result)
          }
        })
        .catch(error => console.log(error))
    }, //search
    imgClick (id) {
      this.videoId = id
      this.youtubeLoading = true
    },
    deleteVideo () {
      let filedata = new FormData()
      filedata.append(
        'filename', this.boardVideo
      )
      filedata.append('flag', '1')
      axios({
        method: 'POST',
        url: 'http:/127.0.0.1:8000/record_delete',
        data: filedata
      })
        .then(response => {
          console.log('sucess', response)
        })
        .catch(response => {
          console.log('Failed', response)
        })
      this.record = 1
    }
  }
}
</script>

<style lang="scss">
#header_ {
  margin-top: 10px;
}
.sticker {
  float: left;
  width: 50%;
  margin-top: 10px;
}
.btn {
  text-align: center;
  width: 50%;
  float: left;
  margin-top: 15px;
}
.youtubeSearch {
  float: right;
  width: 50%;
  text-align: center;
}
.searchbtn {
  margin: 0 auto;
  padding-bottom: 0px !important;
}

.top {
  margin: 30px;
  margin-top: 50px;
}

.videoImg {
  width: 50%;
  float: left;
  padding-right: 5px;
}
.youtube {
  width: 50%;
  float: right;
  padding-left: 5px;
}
.youtubeRe {
  width: 50%;
  float: right;
  padding-top: 0px !important;
}
</style>

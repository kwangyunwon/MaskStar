<template>
  <v-card
    class="content-bg card mx-auto pa-1 transparent"
    flat
    :max-width="card.maxWidth"
    :to="'/BoardDetail/' + board.boardIdx"
  >
    <VueVideoThumbnail
      :show-play-button="showPlayButton"
      :video-src="videoSrc"
      :snapshot-at-duration-percent="parseInt(snapshotPercentage, 10)"
      snapshot-scale-type="contain"
      :snapshot-format="snapshotFormat"
      background-fill-color="black"
      :snapshot-quality="parseFloat(snapshotQuality, 10)"
      :width="parseInt(snapshotWidth, 10)"
      :height="parseInt(snapshotHeight, 10)"
      :chunks-quantity="10"
      @sizeChanged="value => (snapshotSize = value)"
      
    >
      <template #snapshot="{snapshot}">
        <img v-if="snapshot" :src="snapshot" alt="snapshot" />
      </template>
    </VueVideoThumbnail>

    <v-row no-gutters>
      <v-col>
        <v-card-title class="pl-2 pt-3">
          [{{ board.boardSinger }}] {{ board.boardTitle }}
        </v-card-title>

        <v-card-subtitle class="pl-2 pb-0">
          {{ board.authVO.nickname }}<br />
          좋아요 {{ board.boardLike }} <v-icon>mdi-circle-small</v-icon
          ><span style="font-size:5px;">{{ board.uploadDate }}</span>
        </v-card-subtitle>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import VueVideoThumbnail from 'vue-video-thumbnail'

export default {
  components: { VueVideoThumbnail },
  props: {
    board: {
      type: Object,
      required: true
    },
    card: Object
  },
  
  data: () => ({
    videoSrc:'https://s3.us-east-2.amazonaws.com/maskstar.kr/media/',
    snapShotSrc: null,
    snapshotHeight: 350,
    snapshotWidth: 350,
    snapshotPercentage: 10,
    snapshotQuality: 0.5,
    snapshotFormat: 'image/png',
    snapshotSize: 0,
    showPlayButton: false,
  }),
  mounted() {
    this.videoSrc += this.board.boardVideo;
  }
}

</script>

<style></style>

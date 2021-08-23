<template>
<div>
  <v-container v-for="(rank,index) in ranks" :key=index fluid >
      <v-card class="transparent" flat>
        <h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 이 주의 {{rank.title}} <v-icon>{{rank.icon}}</v-icon></h3> 
        <v-slide-group 
        class="pa-4"   
        show-arrows = "always"
        center-active 
        style=".v-slide-group__prev{display:block; always}">
          <v-slide-item v-for="i in rank.loading ? 5 : rank.boards.length" :key="i">
            <v-skeleton-loader
              type="card-avatar"
              :loading="rank.loading"
              width="350px"
              class="mr-1"
            >
            <video-card
              
                :card="{ maxWidth: 500 }"
                :board="rank.boards[i-1]"
            ></video-card>
            </v-skeleton-loader>
            
          </v-slide-item>
        </v-slide-group>
        <v-divider/>
      </v-card>
      
    </v-container>


  
</div>
</template>

<script>
  
  import videoCard from '@/components/VideoCard'
  import axios from 'axios'

  export default {
    name: 'Home',
  data(){
    return{
     ranks:[
        { 
          title: '복면가왕',
          loading: true,
          board:{},
          boards: [],
          icon:'mdi-drama-masks',
        },
        { 
          title: '발라드',
          genre:0,
          loading: true,
          board:{},
          boards: [],
          icon: 'mdi-playlist-music'
        },
        { 
          title: '힙합',
          genre:1,
          loading: true,
          board:{},
          boards: [],
          icon:'mdi-microphone-variant'
        },
        { 
          title: '댄스',
          genre:2,
          loading: true,
          board:{},
          boards: [],
          icon: 'mdi-music'
        },
         { 
          title: '기타',
          genre:3,
          loading: true,
          board:{},
          boards: [],
          icon: 'mdi-music-circle'
        }
      ] 
    }
    
    
  },
  components: {
      videoCard
  },
  mounted() {
    axios
		.get('https://api.maskstar.kr/board/toprankall')
		.then(response=>(this.ranks[0].boards=response.data))
		.catch(error=>{
		console.log(error);
		this.errored=true;
		})
		.finally(()=>this.ranks[0].loading=false)

    axios
		.get('https://api.maskstar.kr/board/toprankgenre/'+this.ranks[1].genre)
		.then(response=>(this.ranks[1].boards=response.data))
		.catch(error=>{
		console.log(error);
    this.errored=true;
		})
		.finally(()=>this.ranks[1].loading=false)

     axios
		.get('https://api.maskstar.kr/board/toprankgenre/'+this.ranks[2].genre)
		.then(response=>(this.ranks[2].boards=response.data))
		.catch(error=>{
		console.log(error);
    this.errored=true;
		})
		.finally(()=>this.ranks[2].loading=false)
     axios
		.get('https://api.maskstar.kr/board/toprankgenre/'+this.ranks[3].genre)
		.then(response=>(this.ranks[3].boards=response.data))
		.catch(error=>{
		console.log(error);
    this.errored=true;
		})
		.finally(()=>this.ranks[3].loading=false)
    axios
		.get('https://api.maskstar.kr/board/toprankgenre/'+this.ranks[4].genre)
		.then(response=>(this.ranks[4].boards=response.data))
		.catch(error=>{
		console.log(error);
    this.errored=true;
		})
		.finally(()=>this.ranks[4].loading=false)

    },//mounted
    created(){
      this.$emit('create_routerview', 'home');
    }
    

    
  }
</script>
<style lang="scss">
.v-slide-group__prev{display:flex;}
</style>

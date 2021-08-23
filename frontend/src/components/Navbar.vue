<template>
  <nav id="navbar">
    <v-app-bar flat app clipped-left light color="#4a148c">
      <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title  class=" mt-3">
        <img style="width:50px;" src="@/assets/image/mic3.png"/>
       <v-btn text plain class="mb-3 font-weight-bold" color="white" to="/" @click="searchText=''"><span style="font-size:16px">MaskStar</span></v-btn>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      
      <v-flex shrink class="hidden-sm-and-down" v-if="(componentName!='mypage') && (componentName!='record')">
          <v-select
          v-model="selected"
          :items="searchOption"
          solo
          outlined
          flat
          hide-details
          dense
        ></v-select>
      </v-flex>
      <v-text-field
        flat
        hide-details
        append-icon="mdi-magnify"
        placeholder="검색"
        outlined
        dense
        v-model="searchText"
        @click:append="search"
        @keyup.enter="search"
        class="hidden-sm-and-down"    
        v-if="(componentName!='mypage') && (componentName!='record')"    
      ></v-text-field>
      <v-spacer></v-spacer>
      <div style="cursor: pointer" @click="move()">
        <v-icon  large color="white">mdi-cloud-upload</v-icon>&nbsp;
        <span style="font-size:17px; color:white">업로드</span>
      </div>
      <AccountList />
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      color="#212121"
      app
      :clipped="$route.name !== 'Watch'"
      :temporary="$route.name === 'Watch'"
      id="nav"
    >
      <div tag="div" class="v-navigation-drawer__content" style="margin-top:10px">
        <v-list dense nav class="py-0" tag="div">
          <v-list-item
            :class="{
              'hidden-lg-and-up': $route.name === 'Watch' ? false : true
            }"
          >
            <v-app-bar-nav-icon
              @click="drawer = !drawer"
              class="mr-5"
            ></v-app-bar-nav-icon>
            <v-toolbar-title class="font-weight-bold">MaskStar</v-toolbar-title>
          </v-list-item>
          <v-divider class="hidden-lg-and-up" light></v-divider>
          <div v-for="parentItem in items" :key="parentItem.header">
            <v-subheader
              v-if="parentItem.header"
              class="pl-3 py-4 subtitle-1 font-weight-bold"
              >{{ parentItem.header }}
              </v-subheader
            >
            
            <v-list-item
              v-for="item in parentItem.pages"
              :key="item.title"
              link
              class="mb-0" 
              :to="item.link" 
              exact
              active-class="active-item"
              @click="searchText=''"
            >
              <v-list-item-icon v-if="parentItem.header !== 'Subscriptions'" >
                <v-icon class="v-icon notranslate mdi theme--dark">{{ item.icon }}</v-icon>
              </v-list-item-icon>
              <v-list-item-avatar v-else class="mr-5">
                <img
                  :src="item.img"
                />
              </v-list-item-avatar>
              <v-list-item-content >
                <v-list-item-title :href="item.link" target="_blank" ><span style="color:white; font-size:15px">{{
                  item.title
                }}</span></v-list-item-title>
              </v-list-item-content>
            </v-list-item>


            <v-divider class="mt-2 mb-2"></v-divider>
          </div>
          
         <div class="footer"> 
            <span v-for="link in links" :key="link.text">

              <span v-if="link.text === 'Terms'" class="mb-2 d-block"> </span>
              <v-btn
                href
                :to="link.link"
                
                text
                small
                color="white"
                
                ><span style="color:white; font-size:5px" >{{ link.text }}</span></v-btn
              >
            </span>
         </div>
        </v-list>
      </div>
    </v-navigation-drawer>
  </nav>
</template>

<script>
import AccountList from '@/components/AccountList'
// 삭제
export default {
  data: () => ({
    drawer: false,
    items: [
      {
        header: null,
        pages: [
          { title: 'Home', link: '/', icon: 'mdi-home' },
        ]
      },
      {
        header: null,
        pages: [
          {
            title: '힙합',
            link: '/hiphop',
            icon: 'mdi-microphone-variant'
          },
          {
            title: '댄스',
            link: '/dance',
            icon: 'mdi-music'
          },
          {
            title: '발라드',
            link: '/ballade',
            icon: 'mdi-playlist-music'
          },
          {
            title: '기타',
            link: '/etc',
            icon: 'mdi-music-circle'
          }
        ]
      },
    ],
    links: [
      { text:'Home', link: '/' ,icon:''},
      { text:'About', link: '/github' ,icon:''},
      { text:'onlinemaskinfra@gmail.com', link: '#' },
      { text:'©MaskStar.All rights Reserved.', link: '#' },
    ],
    selected: '노래',
    searchOption:['노래','가수','유저'],
    searchText: ''
  }),
  props: {
    componentName: {
      type: String,
      required: true
    }
  },
  components: {
      AccountList
      //삭제
    },
  methods: {
    move(){
      if(localStorage.getItem('id')!=null){
        this.$router.push({
          name: "Record",
        });
        this.searchText='';
      }else{
        alert("로그인을 해주세요")
      }
    },
    search() {      
      if (!this.searchText) return
      this.$router.push({
        name: "SearchResult",
        query: { 
          genre:this.componentName,
          keyword: this.searchText ,
          searchOption : this.selected,
          t: new Date().getTime()
          }
      });
      this.searchText='';
    }//search

  },
  mounted() {
    this.drawer = this.$vuetify.breakpoint.mdAndDown ? false : true
    this.drawer = this.$route.name === 'Watch' ? false : this.drawer
  }
}
</script>

<style lang="scss">

.footer {
    position: absolute;

    left: 0;
    text-align: center;
    bottom: 0;

    width: 100%;
}

.v-select__selections input { 
  display: none;
  }
#cate{
    width: 7%;
    text-align: center;
}
fieldset{
  background-color: white;
}
#navbar {
  ::-webkit-scrollbar{
    display: none;
  }

  .active-item {
    .v-list-item__icon {
      color: red !important;
    }
  }
  .v-navigation-drawer__border {
    width: 0 !important;
  }
  

  .vuebar-element {
    height: 250px;
    width: 100%;
    max-width: 500px;
  }

  .vb > .vb-dragger {
    z-index: 5;
    width: 10px;
    right: 0;
  }

  .vb > .vb-dragger > .vb-dragger-styler {
    -webkit-backface-visibility: hidden;
    backface-visibility: hidden;
    -webkit-transform: rotate3d(0, 0, 0, 0);
    transform: rotate3d(0, 0, 0, 0);
    -webkit-transition: background-color 100ms ease-out, margin 100ms ease-out,
      height 100ms ease-out;
    transition: background-color 100ms ease-out, margin 100ms ease-out,
      height 100ms ease-out;

    margin: 5px 5px 5px 0;
    border-radius: 20px;
    height: calc(100% - 10px);
    display: block;
  }

  .v-navigation-drawer__content:hover .vb > .vb-dragger > .vb-dragger-styler {
    width: 10px;
  }

  .vb.vb-scrolling-phantom > .vb-dragger > .vb-dragger-styler {
    background-color: rgba(48, 121, 244, 0.3);
    background-color: rgba(255, 255, 255, 0.3);
  }

  .vb > .vb-dragger:hover > .vb-dragger-styler {
    margin: 0px;
    width: 10px;
  }

  .vb.vb-dragging > .vb-dragger > .vb-dragger-styler {
    background-color: rgba(48, 121, 244, 0.5);
    margin: 0px;
    height: 100%;
  }

  .vb.vb-dragging-phantom > .vb-dragger > .vb-dragger-styler {
    background-color: rgba(48, 121, 244, 0.5);
  }
  .theme--light.v-divider {
    border-color:grey !important; 
  }
  
}
 
</style>

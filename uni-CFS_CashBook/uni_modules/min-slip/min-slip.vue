<template>
	<view class="min-slip" :style="{height:isHeight+'rpx'}">
			 <view  @touchmove ='isStart' @touchstart='start' @touchend='end' class="main-left"  :style="{backgroundColor:theme,transition: 'transform 300ms' , transform: 'translateX(' + left + ')'}">
				<slot></slot>
			 </view>
			 <view class="main-right" :style="{lineHeight:isHeight+'rpx',color:btntxtcolor,width: isW,transition: '300ms',backgroundColor:btncolor}" @click.stop="event">
				{{btntxt}}
			 </view>
	</view>
</template>

<script>
	/*
	*	作者： sonders
	*	邮箱：778277514@qq.com
	* */ 
	export default {
		props:{
			theme:{
				type:String,
				default:'#007AFF'
			},
			btncolor:{
				type:String,
				default:'#4CD964'
			},
			btntxt:{
				type:[String,Number],
				default:'确定'
			},
			btntxtcolor:{
				type:String,
				default:'#fff'
			},
			rightwidth:{
				type:Number,
				default: 200
			},
			isHeight:{
				type: Number,
				default: 100
			}
		},
		data(){
			return {
				left:0,
				startPosition:0,
				isW:'0'
			}
		},
		methods:{
			isStart(e){
				this.isW = this.rightwidth + 'rpx'
				if(e.touches[0].clientX  -  this.startPosition > 0){
						this.isW  = 0
						this.left  =this.isW
				}else{
					this.left  = 0
				}
			},
			start(e){
				this.startPosition =  e.touches[0].clientX
			},
			end(e){
				setTimeout(()=>{
					this.isW = 0
				},2000)
			},
			event(){
				this.$emit('click-o',true)
			}
		}
	}
</script>

<style>
	.min-slip{
		width: 100vw;
		height: 100rpx;
		margin: 50rpx 0;
		overflow: hidden;
		background: #007AFF;
		display: flex;
	}
	.main-left{
		height: 100%;
		flex: 1;
	}
	.main-right{
		height: 100%;
		text-align: center;
	}
</style>

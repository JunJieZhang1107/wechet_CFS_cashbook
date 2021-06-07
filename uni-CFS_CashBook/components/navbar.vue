<template>
	<view>
		<uni-nav-bar>
			<view class="navBarLeft">
				<view slot="left" @tap="clickhome">首页</view>
			</view>
			<view class="navBarTitle">
				<view @tap="clickmessage">账户信息</view>
			</view>
			<view class="navBarRight">
				<view slot="right" @tap="clickadd">添加账户</view>
			</view>
		</uni-nav-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			};
		},
		mounted() {
			
		},
		methods:{
			clickhome(){
				this.$emit('clickhome')
			},
			clickadd(){
				// uni.reLaunch({
				// 	url:"../Add/Add",
				// 	success: (res) => {
				// 		console.log("跳转Add成功",res)
				// 	},
				// 	fail: (err) => {
				// 		console.log("跳转Add失败",err)
				// 	}
				// })
				this.$emit('clickadd')
			},
			clickmessage(){
				// uni.reLaunch({
				// 	url: "../Message/Message",
				// 	success: (res) => {
				// 		console.log("跳转message成功",res)
				// 	},
				// 	fail: (err) => {
				// 		console.log("跳转message失败",err)
				// 	}
				// })
				this.$emit('clickmessage')
			}
		}
	}
</script>

<style>
.navBarLeft{
	/* background-color: #0000FF; */
	width: 250rpx;
	text-align: center;
	font-size: 30rpx;
}
.navBarTitle{
	width: 250rpx;
	font-size: 30rpx;
	text-align: center;
}
.navBarRight{
	width: 250rpx;
	text-align: center;
	font-size: 30rpx;
}
</style>

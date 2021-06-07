<template>
	<view>
		<view class="status_bar"></view>
		<remind-nav-bar :current="2"></remind-nav-bar>
		<tab-bar :current="3"></tab-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		props: {
			
		},
		created(){
			uni.reLaunch({
				url: '../../../Message/Message'
			})
		},
		methods: {
		}
	}
</script>

<style>
.side{
	display: flex;
	flex-direction: column;
}
.side>view{
	width: 100rpx;
	height: 200rpx;
	margin-top: 100rpx;
}
.side>view>text{
	width: 30rpx;
	display: block;
	text-align: center;
	line-height: 50rpx;
	padding-left: 25rpx;
}
</style>

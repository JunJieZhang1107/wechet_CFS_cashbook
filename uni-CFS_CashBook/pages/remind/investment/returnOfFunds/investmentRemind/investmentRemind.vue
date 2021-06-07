<template>
	<view>
		<view class="status_bar"></view>
		<remind-nav-bar :current="2"></remind-nav-bar>
		<view class="investmentRemind">
			<view class="side">
				<view @click="toLoanAmount">
					<text>外借资金</text>
				</view>
				<view class="bgColor">
					<text>投资资金</text>
				</view>
			</view>
			<scroll-view scroll-y="true" :scroll-top="scrollTop" class="scrollY">
				<view class="content">
					<uni-notice-bar :scrollable="true" :single="true" text="点击进入详情" />
					<uni-list class="lists" v-for="(item,index) in listData" :key="index">
						<!-- 每个账户对应的资料信息 -->
						<uni-list-item clickable @click="onClick(item,index)" 
						:title='"投资金额："+item.productName' 
						:note='"到期时间："+item.backTime'>
						</uni-list-item>
					</uni-list>
				</view>
			</scroll-view>

		</view>
		<tab-bar :current="3"></tab-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				listData: [],
			}
		},
		
		methods: {
			// 获取投资数据
			getData() {
				uni.getStorage({
					key: 'userId',
					success: (res) => {
						this.userId = res.data
						// 获取用户账号数据
						uni.request({
							url: 'http://localhost:8080/invest',
							method: 'GET',
							data: {
								userId: res.data
							},
							success: (res) => {
								this.listData = res.data;
							}
						})
					}
				});
			},
			onClick(item, index) {
				this.getData();
				console.log(item);
				uni.reLaunch({
					url: './detail/detail?investId='+item.investId+"&accountId="+item.accountId
				})
			},
			toLoanAmount() {
				uni.reLaunch({
					url: '../loanAmount/loanAmount'
				})
			}
		},
		created(){
			this.getData();
		}
	}
</script>

<style>
	.investmentRemind {
		display: flex;
	}

	.side {
		display: flex;
		flex-direction: column;
		width: 100rpx;
	}

	.side>view {
		width: 100rpx;
		height: 200rpx;
		margin-top: 100rpx;
	}

	.side>view>text {
		width: 30rpx;
		display: block;
		text-align: center;
		line-height: 50rpx;
		padding-left: 25rpx;
	}

	.bgColor {
		background-color: #0000FF;
	}

	.content {
		height: 800rpx;
		width: 800rpx;
		position: absolute;
		left: 50rpx;
	}

	.nav-list {
		display: flex;
		flex-direction: column;
		height: 200rpx;
	}

	.scrollY {
		height: 800rpx;
	}
</style>

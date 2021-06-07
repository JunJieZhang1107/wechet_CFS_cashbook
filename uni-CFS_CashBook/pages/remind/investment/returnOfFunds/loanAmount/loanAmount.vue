<template>
	<view>
		<view class="status_bar"></view>
		<remind-nav-bar :current="2"></remind-nav-bar>
		<view class="loanAmount">
			<view class="side">
				<view class="bgColor">
					<text>外借资金</text>
				</view>
				<view @click="toInvestmentRemind">
					<text>投资资金</text>
				</view>
			</view>
				<scroll-view scroll-y="true" :scroll-top="scrollTop" class="scrollY">
					<view class="content">
						<view v-for="(item,index) in arr" :key="index">
							<min-slip 
							theme="#fff" 
							btntxt="归还" 
							btntxtcolor="#000" 
							rightwidth="370"
							btncolor="red"
							isHeight="180"
							@click="deleteItem(item)"
							>
									<view class="nav-list" >
									<text >借出金额 {{item.borrowMoney}}</text>
									<text>名字 {{item.borrowName}}</text>
									<text>预计归还时间 {{item.backTime}}</text>
								</view>
							</min-slip>
						</view>
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
				arr:[]
			}
		},
		methods: {
			// 获取外借资金数据
			getData(){
				uni.getStorage({
					key: 'userId',
					success: (res) => {
						this.userId = res.data
						// 获取用户账号数据
						uni.request({
							url: 'http://localhost:8080/borrow',
							method: 'GET',
							data: {
								userId: res.data
							},
							success: (res) => {
								this.arr = res.data;
							}
						})
					}
				});
			},
			deleteItem(item){
				
				uni.request({
					url: 'http://localhost:8080/borrow',
					method: 'PUT',
					data: {
						id: item.id,
						accountId: item.accountId,
						borrowMoney:item.borrowMoney
					},
					success: (res) => {
						if(res.data.msg =="回款成功")
						{
							uni.showToast({
								title:res.data.msg,
							})
							this.getData();
						}else{
							uni.showToast({
								title:res.data.msg,
								icon:'none'
							})
						}
					}
				})
			},
			toInvestmentRemind(){
				uni.reLaunch({
					url: "../investmentRemind/investmentRemind"
				})
			}
		},
		created(){
			this.getData();
		}
	}
</script>

<style>
.loanAmount{
	display: flex;
}
.side{
	display: flex;
	flex-direction: column;
	width: 100rpx;
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
.bgColor{
	background-color: #0000FF;
}
.content{
	height: 800rpx;
	width: 200rpx;
	position: absolute;
	left: 50rpx;
}
.nav-list{
	display: flex;
	flex-direction: column;
	height: 180rpx;
}
.scrollY{
	height: 800rpx;
}
</style>

<template>
	<view class="income">
		<view class="status_bar"></view>
		<inan-nav-bar :current='0'></inan-nav-bar>
		<uni-forms>
			<uni-forms-item label="选择账户:" v-model="selectAccount">
				<picker mode="selector" :value="selectIndex" :range="selectAccount" @change="pinckSelect">
					<view class="pickerView">{{ selectAccount[selectIndex] }}</view>
				</picker>
			</uni-forms-item>
			<uni-forms-item label="收入金额:" >
				<input v-model="income" type="number" placeholder="请输入收入金额"/>
				<!-- <uni-number-box @change="numberBox" max="100000" min="0"></uni-number-box> -->
			</uni-forms-item>
			<uni-forms-item label="原因:" name="">
				<input type="text" v-model="reason" placeholder="请输入原因" />
			</uni-forms-item>
			<button class="income-button" @click="submit">提交收入信息</button>
		</uni-forms>
		<tab-bar :current="1"></tab-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				selectAccount: [],	// 选择账户
				income: 0,	// 收入金额
				selectAccountOtherMsg:[], // 账户其他数据
				reason: '',			// 原因
				selectIndex: 0,
				userId: 0 // 用户id
			}
		},
		methods: {
			numberBox(e){
				console.log(e)
			},
			pinckSelect(e){
				this.selectIndex = e.target.value
			},
			// 提交数据
			submit(){
				var tmp = this.selectAccountOtherMsg[this.selectIndex];
				var accountId = tmp.accountId;
				var type = true;
				var money = this.income;
				var reason = this.reason;
				if(money > 0 || reason.length > 0){
					uni.request({
						url: 'http://localhost:8080/charge',
						method: 'POST',
						data:{
							accountId : accountId,
							type : type,
							money : money,
							reason : reason,
						},
						success: (res) => {
							uni.showToast({
								title: "提交成功",
							})
						}
					})
				}else{
					uni.showToast({
						title: "不可为空，金额>0",
						icon:'none'
					})
				}
			},
			// 获取所有账号数据
			getAllAccount(){
				uni.getStorage({
					key: 'userId',
					success: (res) => {
						this.userId = res.data
						// 获取用户账号数据
						uni.request({
							url: 'http://localhost:8080/accounts',
							method: 'GET',
							data:{
								userId : res.data
							},
							success: (res) => {
								this.selectAccount = [];
								this.selectAccountOtherMsg = [];
								res.data.forEach((item,index,array)=>{
									this.selectAccount.push(item.accountName);
									var tmp = {};
									tmp.accountId = item.accountId;
									tmp.money = item.accountFreeMoney;
									this.selectAccountOtherMsg.push(tmp);
								})
							}
						})
					}
					});
				},
			},
		created(){
			this.getAllAccount();
		}
	}
</script>

<style>
.income{
	width: 100%;
}
.nav-bar{
	width: 100%;
	height: 44px;
	display: flex;
	border-bottom: 1px solid #AAAAAA;
}
.nav-bar-list{
	flex-direction: column;
	flex-grow: 1;
	text-align: center;
	line-height: 44px;
}
.bgColor{
	background-color: #0000FF;
}
.bgRed{
	background-color: red;
}
.income-form{
	width: 80%;
	height: 500rpx;
	margin-left: 10%;
	margin-top: 50%;
	transform: translate(0,-50%);
	/* background-color: red; */
}
uni-forms{
	display: block;
	margin-top: 50rpx;
	margin-left: 20rpx;
}
.pickerView,input{
	margin-top: 15rpx;
}
.income-button{
	margin-top: 50rpx;
	font-size: 32rpx;
	width: 500rpx;
}
</style>

<template>
	<view>
		<view class="status_bar"></view>
		<inan-nav-bar :current="3"></inan-nav-bar>
		<uni-forms class="lend">
			<uni-forms-item label="选择账户" name="">
				<picker mode="selector" :range="selectAccount" :value="selectIndex" @change="pickerSelect">
					<view class="pinckerView">{{ selectAccount[selectIndex] }}</view>
				</picker>
			</uni-forms-item>
			<uni-forms-item label="借出金额" name="">
				<input type="number" value="" v-model="this.lend" placeholder="请输入借出金额" />
			</uni-forms-item>
			<uni-forms-item label="名字" name="">
				<input type="text" v-model="name" value="" placeholder="请输入外借的名字" />
			</uni-forms-item>
			<uni-forms-item label="预计归还时间" name="">
				<picker mode="date" :value="date" :start="date" @change="pickDate">
					<view>{{ date }}</view>
				</picker>
			</uni-forms-item>
			<button type="default" @click="doSubmit">提交借出信息</button>
		</uni-forms>
		<tab-bar :current="1"></tab-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				selectAccount: ['无其他账户'], // 选择账户
				selectAccountOtherMsg: [], // 账户其他数据
				lend: '', // 借出金额
				date: '', // 预计归还日期
				selectIndex: 0,
				name: ''
			}
		},
		mounted() {
			this.getTime()
		},
		methods: {
			doSubmit() {
				// 当前账户金额 < 借出金额。 则提示无法借出
				if (this.selectAccountOtherMsg[this.selectIndex].money < this.lend) {
					uni.showToast({
						title: "超出该账户金额量",
						icon: "none"
					})
				} else {
					uni.request({
						url: 'http://localhost:8080/borrow',
						method: 'POST',
						data: {
							accountId: this.selectAccountOtherMsg[this.selectIndex].accountId,
							borrowMoney: this.lend,
							backTime: this.date,
							borrowName: this.name
						},
						success: (res) => {
							if (res.data.msg == "插入成功") {
								uni.showToast({
									title: "提交成功",
								})
								this.getAllAccount();
							} else {
								uni.showToast({
									title: "提交失败",
									icon: 'none'
								})
							}

						}
					})
				}
			},
			// 获取所有账号数据
			getAllAccount() {
				uni.getStorage({
					key: 'userId',
					success: (res) => {
						this.userId = res.data
						// 获取用户账号数据
						uni.request({
							url: 'http://localhost:8080/accounts',
							method: 'GET',
							data: {
								userId: res.data
							},
							success: (res) => {
								this.selectAccount = [];
								this.selectAccountOtherMsg = [];
								res.data.forEach((item, index, array) => {
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
			pickDate(e) {
				this.date = e.target.value
			},
			getTime() {
				var date = new Date(),
					year = date.getFullYear(),
					month = date.getMonth() + 1,
					day = date.getDate()
				month >= 1 && month <= 9 ? (month = "0" + month) : "";
				day >= 0 && day <= 9 ? (day = "0" + day) : "";
				var timer = year + '-' + month + '-' + day;
				this.date = timer
			},
			pickerSelect(e) {
				this.selectIndex = e.target.value
			}
		},
		created() {
			this.getAllAccount();
		}
	}
</script>

<style>
	.pinckerView {
		display: block;
		margin-top: 20rpx;
	}

	input {
		display: block;
		margin-top: 15rpx;
		border: none;
	}

	.lend {
		display: block;
		margin-top: 20rpx;
		margin-left: 20rpx;
	}

	button {
		width: 500rpx;
	}
</style>

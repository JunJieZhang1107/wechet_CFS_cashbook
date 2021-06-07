<template>
	<view>
		<view class="status_bar"></view>
		<inan-nav-bar :current="2"></inan-nav-bar>
		<uni-forms>
			<uni-forms-item label="选择账户" name="">
				<picker mode="selector" Lvalue="selectIndex" :range="selectAccount" @change="pickerSelect">
					<view class="pinckerView">{{ selectAccount[selectIndex] }}</view>
				</picker>
			</uni-forms-item>
			<uni-forms-item label="负债金额" name="">
				<input type="number" value="" v-model="this.liabilities" placeholder="请输入负债金额"/>
			</uni-forms-item>
			<uni-forms-item label="最晚还款日期" name="">
				<picker mode="date" :value="date" :start="date"  @change="pickDate">
					<view>{{ date }}</view>
				</picker>
			</uni-forms-item>
			<button type="default" @click="doSubmit">提交负债信息</button>
		</uni-forms>
		<tab-bar :current="1"></tab-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				selectAccount: ['无账户信息'] ,// 选择账户
				selectAccountOtherMsg:[], // 账户其他数据
				liabilities: 0, // 负债金额
				date: new Date() ,// 还款日期
				selectIndex: 0
			}
		},
		props:{
			title: { type:String, default: ''},
			backgroundColor: { type: String, default: '#0000FF' }
		},
		mounted(){
			this.getTime()
		},
		methods: {
			doSubmit(){
				if(this.selectAccountOtherMsg[this.selectIndex].money*4 <= this.liabilities){
					uni.showToast({
						title: "金额过大，不能提交（超过4倍)",
						icon: "none"
					})
				}else{
					// 提交数据
					uni.request({
						url: 'http://localhost:8080/debt',
						method: 'POST',
						data:{
							accountId: this.selectAccountOtherMsg[this.selectIndex].accountId,
							debtPay: this.liabilities,
							payTime: this.date
						},
						success: (res) => {
							console.log();
							if(res.data.msg == "插入成功"){
								// 清空数据
								this.liabilities = 0;
								uni.showToast({
									title: "提交成功",
								})
							}else{
								uni.showToast({
									title: "提交失败",
									icon: "none"
								})
							}
						}
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
			pickDate(e){
				this.date = e.target.value
			},
			getTime(){			
				var date = new Date(),
				year = date.getFullYear(),
				month = date.getMonth() + 1,
				day = date.getDate()
				month >= 1 && month <= 9 ? (month = "0" + month) : "";
				day >= 0 && day <= 9 ? (day = "0" + day) : "";
				var timer = year + '-' + month + '-' + day;
				this.date = timer
			},
			pickerSelect(e){
				this.selectIndex = e.target.value
			},
			clickTitle(index){
				console.log(index)
				this.TabIndex = index
				switch(index){
					case 0:
						uni.reLaunch({
							url: "../inandout"
						})
						break
					case 1:
						uni.reLaunch({
							url: '../income/income'
						})
						break
					
				}
			}
		},
		created() {
			this.getAllAccount();
		}
	}
</script>

<style>
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
.pinckerView{
	display: block;
	margin-top: 20rpx;
}
input{
	display: block;
	margin-top: 15rpx;
}
button{
	width: 500rpx;
}
</style>

<template>
	<view>
		<view class="status_bar"></view>
		<inan-nav-bar :current="1"></inan-nav-bar>
		<view class="expenditure">
			<uni-forms>
				<uni-forms-item label="选择账户" name="selectAccount">
					<picker mode="selector" :value="selectIndex" :range="selectAccount" @change="pickerSelector">
						<view class="pickerView">{{ selectAccount[selectIndex] }}</view>
					</picker>
				</uni-forms-item>
				<uni-forms-item label="选择预算类型" name="budgetType">
					<picker :value="index" :range="arr" @change="pinckerBudget">
						<view class="pickerView2">{{ arr[index] }}</view>
						<view>{{ money[index] }}</view>
					</picker>
						
				</uni-forms-item>
				<uni-forms-item label="支出金额" name="expenditureAmount">
					<input type="number" v-model="expenditureAmount" placeholder="输入支出金额"/>
				</uni-forms-item>
				<uni-forms-item label="原因" name="reason">
					<input type="text" v-model="reason" placeholder="支出原因"/>
				</uni-forms-item>
				<button type="default" @click="doSubmit">提交支出信息</button>
			</uni-forms>
		</view>
		<tab-bar :current="1"></tab-bar>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				userId:-1,
				selectAccount: ['邮政储蓄','银联'],	// 选择账户
				selectAccountOtherMsg:[], // 账户其他数据
				budgetType: '',		// 选择预算类型
				expenditureAmount: 0,	// 支出金额
				reason: '',		// 支出原因
				TabIndex: 2,
				selectIndex: 0,
				index: 0, // arr 和 money的 index下标
				arr: ['吃','喝','玩','乐','提升'],  // 获取预算数据
				money: [] ,// 对应的预算金额
				id: -1, // 预算表对应的id
			}
		},
		methods: {
			doSubmit(){ // 提交数据校验
				// 1. 当前账户流动资金要大于支出金额
				if(this.selectAccountOtherMsg[this.selectIndex].money >= this.expenditureAmount &&this.money[this.index] >= this.expenditureAmount )
				{
					uni.request({
						url: 'http://localhost:8080/solveExpend',
						method: 'POST',
						data:{
							budgetId : this.id,
							accountId: this.selectAccountOtherMsg[this.selectIndex].accountId,
							budgetType: this.index,
							spendMoney: this.expenditureAmount,
							reason : this.reason
						},
						success: (res) => {
							// 将预算扣除和对应金额扣除
							this.selectAccountOtherMsg[this.selectIndex] -= this.expenditureAmount;
							this.money[this.selectIndex] -= this.expenditureAmount;
							this.expenditureAmount = 0;
							uni.showToast({
								title: res.data.msg,
							})
						 }
						})					
				}else{
					uni.showToast({
						title: '提交失败',
						icon: 'none'
						})
				}
			},
		
			pickerSelector(e){
				this.selectIndex = e.target.value
			},
			pinckerBudget(e){
				this.index = e.target.value
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
									console.log(this.selectAccount);
								})
							}
						})
					}
				});
			},
		// 获取预算数据
		getBudget(){
			uni.getStorage({
				key:'userId',
				success: (res) => {
					console.log("预算表",res.data);
					this.userId = res.data
					// 获取用户数据
					uni.request({
						url: 'http://localhost:8080/budget',
						method: 'GET',
						data: {
							userId: res.data
						},
						success: (res) => {
							if(res.data){
								this.money[0] = res.data.eat,
								this.money[1] = res.data.drink,
								this.money[2]= res.data.play,
								this.money[3] = res.data.funny,
								this.money[4] = res.data.improve,
								this.id = res.data.id;
							}
						}
					})
				}
			})
		}
		},
		created(){
			this.getAllAccount();
			this.getBudget();
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
.pinckerColor{
	color: #999999;
	font-size: 32rpx;
}
.pickerView{
	display: block;
	padding-top: 20rpx;
}
input{
	display: block;
	margin-top: 15rpx;
}
button{
	width: 500rpx;
}
.expenditure{
	margin-left: 20rpx;
	margin-top: 20rpx;
}
</style>

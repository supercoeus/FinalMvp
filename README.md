# FinalMvp
<h2>mvp模式介绍</h2>
<pre  style="background:#ffffff;">
  <img src="http://a.hiphotos.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=0d3000fa9c25bc313f5009ca3fb6e6d4/8b82b9014a90f603534849733c12b31bb051ed0e.jpg"/>
  
  由图可见,mvp模式将分为View、presenter、model，而presenter将连接View和model，
  view和model之间不允许直接交互.
  将view和model完全隔绝，可以说完全解决了耦合性的问题。
  道理很容易理解，可是要想真正做好却不那么容易！
</pre>
<h2>finaMvp介绍</h2>
<pre style="background:#ffffff;">
  finalMvp是为了轻松使用mvp模式开发，并且规范mvp代码而编写的框架。
  该框架的结构为:BaseView BasePresenter BaseModel
  代码中直接继承这三类,变可以轻松实现mvp分层
  finalMvp架构模型图:
  <img src="https://github.com/yuanfen7650/FinalMvp/blob/master/README/finaMvpDXF.png?raw=true"/>
</pre>
<h2>代码实现</h2>
<pre>
 1、activity继承BaseView
 	onDataChanage()//当数据变化时通知此方法
 	onChanageUi()//presenter可以直接发送修改ui指令,在onChangeUi接收
 	编辑数据字段加上注解@ViewData,presenter会使用依赖注入修改数据
 2、编辑presenter继承BasePresenter
 	initModel()//初始化model
 	onModelCallBack//数据返回后的监听
 	执行loadData()//请求数据，会发送到model的loadData
 	执行setDataChanage(data)//将view中标注@ViewData的数据值修改为data,并通知view改变ui
 3、编辑model继承BaseModel
 	loadData(tag,msg)//加载数据
 	chanageData(tag,data)//数据加载完成后调用这个方法
</pre>

<h2>demo代码</h2>
Activity编辑实现BaseView
<pre>
public class MainActivity extends Activity implements BaseView{
	private MainPresenter preseneter;
	private TextView tv;
	/**
	 * ViewData便是数据源注解，通过此注解可以自动修改值
	 */
	@ViewData
	public String data="aaaaaaaaaaaaaaaaaaaaaaa";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preseneter=new MainPresenter(this);
        tv=new TextView(this);
        setContentView(tv);
        tv.setText(data);
        tv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				preseneter.chanageTextView();
			}
		});
    }
	@Override
	public void onDataChanage() {
		tv.setText(data);
	}
	@Override
	public void onChanageUi(int tag) {
	}
}
</pre>
MainPresenter继承BasePresenter
<pre>
public class MainPresenter extends BasePresenter{
	private final int TAG_LOAD_DATA=1;
	public MainPresenter(BaseView view) {
		super(view);
	}
	public void chanageTextView(){
		loadData(TAG_LOAD_DATA, "http://xxxxx");
	}
	/**
	 * 初始化model
	 */
	@Override
	public BaseModel initModel() {
		return new MainModel();
	}
	@Override
	public void onModelCallBack(int tag, Object obj) {
		switch (tag) {
		case TAG_LOAD_DATA:
			/**
			 * 执行setDataChange,代码便会将BaseView中的注解的值替换为obj，
			 * 并执行BaseView下的onDataChanage方法，通知BaseView修改ui
			 */
			setDataChanage(obj);
			break;
		}
	}
}
</pre>
MainModel继承BaseModel
<pre>
public class MainModel extends BaseModel{
	@Override
	public void loadData(final int tag, String msg) {
		new Thread(){
			public void run() {
				//执行耗时操作,如执行http请求,从sqlite中读取或计算数据
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//返回得到计算后的数据,将数据返回
				chanageData(tag, "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
			};
		}.start();
	}
}
</pre>

# FinalMvp
<h2>mvp模式介绍</h2>
<pre  style="background:#ffffff;">
  <img src="http://a.hiphotos.baidu.com/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=0d3000fa9c25bc313f5009ca3fb6e6d4/8b82b9014a90f603534849733c12b31bb051ed0e.jpg"/>
  
  由图可见,mvp模式将分为View、presenter、model，而presenter将连接View和model，view和model之间不允许直接交互.
  将view和model完全隔绝，可以说完全解决了耦合性的问题。
  道理很容易理解，可是要想真正做好却不那么容易！
</pre>
<h2>finaMvp介绍</h2>
<pre style="background:#ffffff;">
  finalMvp是为了轻松使用mvp模式开发，并且规范mvp代码而编写的框架。
  该框架的结构为:BaseView BasePresenter BaseModel
  代码中直接继承这三类,变可以轻松实现mvp分层
  finalMvp架构模型图:
  
</pre>

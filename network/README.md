@GET()： 注解在方法上，表示get请求,括号内为请求url
@POST()： 注解在方法上，表示Post请求,括号内为请求url
@PUT()： Put请求
@DELETE()： Delete请求
@Query()： get请求时，注解在方法中，注解后括号中是键，跟在后面的是值，将会以key=value的方式拼接在url后面
@QueryMap：get请求时，如果参数很多，可以将参数集成到map集合，用此注解在方法中传递
@Field()： Post请求时，注解在方法中，注解后括号中是键，跟在后面的是值，将每一个请求参数都存放至请求体中
@FieldMap：Post请求时，如果参数很多，可以将参数集成到map集合，用此注解在方法中传递
@Body：若Post请求参数有多个，可统一封装到一个类中，用此注解在方法中传递
@Path()： 注解在方法中，注解后括号中的是url上的占位符，跟在后面的是填充到url占位符上的字符串
@FormUrlEncoded()：用于Post请求，将会自动将请求参数的类型调整为application/x-www-form-urlencoded
@Multipart()： 用于Post请求，上传
@Part()： 用于Post请求，注解在上传的方法中，上传的参数
@Headers()： 添加许多请求头，括号内为请求头内容
@Header()： 添加一个请求头，有重名的将会覆盖
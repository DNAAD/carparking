@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
  classes = AppConfig.class,
  loader = AnnotationConfigContextLoader.class)
public class SpringRetryTest {
 
    @Autowired
    private MyService myService;
 
    @Autowired
    private RetryTemplate retryTemplate;
 
    @Test(expected = RuntimeException.class)
    public void givenTemplateRetryService_whenCallWithException_thenRetry() {
        retryTemplate.execute(arg0 -> {
            myService.templateRetryService();
            return null;
        });
    }
}
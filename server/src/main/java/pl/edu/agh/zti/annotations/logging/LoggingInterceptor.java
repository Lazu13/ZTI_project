package pl.edu.agh.zti.annotations.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Spring component being an Aspect
 */
@Component
@Aspect
public class LoggingInterceptor {

    private static final Logger LOGGER = Logger.getLogger(LoggingInterceptor.class.getName());

    /**
     * Around invoke method
     * It is responsible for logging comment with arguments before method call and
     * after method finished to log ending comment
     *
     * @param joinPoint proceeding join point
     * @return Object being any kind of [[ProceedingJoinPoint]] result
     * @throws Throwable being rethrow from proceeding join point
     */
    @Around("@annotation(pl.edu.agh.zti.annotations.logging.Loggable)")
    public Object aroundInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.log(Level.INFO, "Starts with: " + Arrays.toString(joinPoint.getArgs()));
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        }
        LOGGER.log(Level.INFO, "End");
        return result;
    }

}

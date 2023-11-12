# VirtualThreads

Left hand side in the following image is Platform thread & Right hand side is Virtual Thread

![image](https://github.com/reachkvperumal/VirtualThreads/assets/18358866/932bce8a-614b-4518-9fad-22ea39157d64)


These virtual threads are mounted on carrier threads. 
 
- When the virtual thread attempts to use blocking I/O, the JVM transforms this call into a non-blocking one. 
- Unmounts the virtual thread, and mounts another virtual thread on the carrier thread. 
- When the I/O completes, the waiting virtual thread becomes eligible again and will be re-mounted on a carrier thread to continue its execution. 
- For the user, all this dance is invisible. Your synchronous code is executed asynchronously.

Note that the virtual thread may not be re-mounted on the same carrier thread.

Difference between OS Threads & Virtual Threads while iterating for 10,000 times with sleep time of 1 second.

Starting OS THREADS...
Total Elapsed Time for OS THREADS in seconds: 101

Starting VIRTUAL THREADS...
Total Elapsed Time for VIRTUAL THREADS in Seconds: 1

## StructuredTaskScope
Following is the output from StructuredTaskScope execution.

YahooReports[
getSummary={"message":"You have exceeded the rate limit per second for your plan, BASIC, by the API provider"}, 
getFinancials= "meta": {
"symbol": "JPM",
"start": 493590046,
"end": 1699763610,
"timeUnit": "annual"
},
"loading": false,
"errorList": [] 
}
getOptions={"message":"You have exceeded the rate limit per second for your plan, BASIC, by the API provider"}, 
topHoldings={"message":"You have exceeded the rate limit per second for your plan, BASIC, by the API provider"}, 
getEarnings={"message":"You have exceeded the rate limit per second for your plan, BASIC, by the API provider"}]

## ScopedValue - Pending

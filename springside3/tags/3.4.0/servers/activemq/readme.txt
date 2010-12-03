1.bin/active-admin: uncomment JMX options and set JMX port to 1616.
                    set max memory from 512M to 2048M, and set min memory and new generation memory size. 
2.conf/activemq.xml: set <system usage>, 1)memory from 64m to 512m, 2)disk from 100g to 10g, 3)temp from 10g to 1g
                     set <destinationPolicy> memory limit from 1m to 32m
3.conf/activemq-network-broker-1.xml:set networkConnector and transportConnector multicast from default to special group name.
                                     set right broker name.
                               
4.conf/jetty.xml: remove demo,camel and fileserver application.

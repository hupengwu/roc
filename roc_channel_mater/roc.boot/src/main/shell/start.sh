#!/bin/sh
echo "Server starting........................"
binPath=$(dirname $0)
cd $binPath
binPath=$(pwd)

Push_Home=`cd ../;pwd`
echo $Push_Home

for i in `ls $Push_Home/libs`
	do
		JAVA_OPTIONS=$Push_Home/libs/$i
	done

echo "$JAVA_OPTIONS"

	REST_PORT=8003
	DSF_PORT=7003
	export REST_PORT DSF_PORT
    java $JAVA_OPTS $CATALINA_OPTS -Denv.prop=$1 -jar $JAVA_OPTIONS

echo "Rest Express Server started........................"
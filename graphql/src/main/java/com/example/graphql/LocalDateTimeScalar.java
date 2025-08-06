package com.example.graphql;

import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocalDateTimeScalar {
	public static final GraphQLScalarType LOCAL_DATE_TIME = GraphQLScalarType.newScalar()
			.name("LocalDateTime")
			.description("LocalDateTime scalar")
			.coercing(new Coercing<>() {
				@Override
				public String serialize(Object dataFetcherResult) {
					if (dataFetcherResult instanceof LocalDateTime) {
						return ((LocalDateTime) dataFetcherResult).toString();
					}
					throw new CoercingSerializeException("Unable to serialize " + dataFetcherResult + " as LocalDateTime");
				}

				@Override
				public LocalDateTime parseValue(Object input) {
					if (input instanceof String) {
						return LocalDateTime.parse((String) input);
					}
					throw new CoercingParseValueException("Unable to parse " + input + " as LocalDateTime");
				}

				@Override
				public LocalDateTime parseLiteral(Object input) {
					if (input instanceof StringValue) {
						return LocalDateTime.parse(((StringValue) input).getValue());
					}
					throw new CoercingParseLiteralException("Unable to parse " + input + " as LocalDateTime");
				}
			})
			.build();
}
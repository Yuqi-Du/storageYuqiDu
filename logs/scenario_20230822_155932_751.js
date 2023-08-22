scenario.run({
    'driver':       'http',
    'tags':         'block:schema',
    'threads':      '1',
    'docscount':    '500',
    'auth_token':   'AstraCS:OTCvFjqTgAIaYWBQcCUCymau:9b047725e66c58325f34df3d88cf1b2bcafaeaaccf9fd0c38c1c86cac5770549',
    'jsonapi_port': '443',
    'workload':     'vector-test-to-astra',
    'alias':        'vectortesttoastra_default_schema'
});
scenario.run({
    'driver':       'http',
    'tags':         'name:"write.*"',
    'cycles':       '500',
    'threads':      '20',
    'errors':       'timer,warn',
    'docscount':    '500',
    'auth_token':   'AstraCS:OTCvFjqTgAIaYWBQcCUCymau:9b047725e66c58325f34df3d88cf1b2bcafaeaaccf9fd0c38c1c86cac5770549',
    'jsonapi_port': '443',
    'workload':     'vector-test-to-astra',
    'alias':        'vectortesttoastra_default_write'
});
